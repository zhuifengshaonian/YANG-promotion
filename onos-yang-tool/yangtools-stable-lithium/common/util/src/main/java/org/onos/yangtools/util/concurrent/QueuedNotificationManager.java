/*
 * Copyright (c) 2014 Brocade Communications Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.onos.yangtools.util.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.concurrent.GuardedBy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * This class manages queuing and dispatching notifications for multiple listeners concurrently.
 * Notifications are queued on a per-listener basis and dispatched serially to each listener via an
 * {@link Executor}.
 * <p>
 * This class optimizes its memory footprint by only allocating and maintaining a queue and executor
 * task for a listener when there are pending notifications. On the first notification(s), a queue
 * is created and a task is submitted to the executor to dispatch the queue to the associated
 * listener. Any subsequent notifications that occur before all previous notifications have been
 * dispatched are appended to the existing queue. When all notifications have been dispatched, the
 * queue and task are discarded.
 *
 * @author Thomas Pantelis
 *
 * @param <L> the listener type
 * @param <N> the notification type
 */
public class QueuedNotificationManager<L,N> implements NotificationManager<L,N> {

    /**
     * Interface implemented by clients that does the work of invoking listeners with notifications.
     *
     * @author Thomas Pantelis
     *
     * @param <L> the listener type
     * @param <N> the notification type
     */
    public interface Invoker<L,N> {

        /**
         * Called to invoke a listener with a notification.
         *
         * @param listener the listener to invoke
         * @param notification the notification to send
         */
        void invokeListener( L listener, N notification );
    }

    private static final Logger LOG = LoggerFactory.getLogger( QueuedNotificationManager.class );

    private final Executor executor;
    private final Invoker<L,N> listenerInvoker;

    private final ConcurrentMap<ListenerKey<L>,NotificationTask>
                                                          listenerCache = new ConcurrentHashMap<>();

    private final String name;
    private final int maxQueueCapacity;

    /**
     * Constructor.
     *
     * @param executor the {@link Executor} to use for notification tasks
     * @param listenerInvoker the {@link Invoker} to use for invoking listeners
     * @param maxQueueCapacity the capacity of each listener queue
     * @param name the name of this instance for logging info
     */
    public QueuedNotificationManager( Executor executor, Invoker<L,N> listenerInvoker,
            int maxQueueCapacity, String name ) {
        this.executor = Preconditions.checkNotNull( executor );
        this.listenerInvoker = Preconditions.checkNotNull( listenerInvoker );
        Preconditions.checkArgument( maxQueueCapacity > 0, "maxQueueCapacity must be > 0 " );
        this.maxQueueCapacity = maxQueueCapacity;
        this.name = Preconditions.checkNotNull( name );
    }

    /* (non-Javadoc)
     * @see org.onos.yangtools.util.concurrent.NotificationManager#addNotification(L, N)
     */
    @Override
    public void submitNotification( final L listener, final N notification )
            throws RejectedExecutionException {

        if( notification == null ) {
            return;
        }

        submitNotifications( listener, Arrays.asList( notification ) );
    }

    /* (non-Javadoc)
     * @see org.onos.yangtools.util.concurrent.NotificationManager#submitNotifications(L, java.util.Collection)
     */
    @Override
    public void submitNotifications( final L listener, final Iterable<N> notifications )
            throws RejectedExecutionException {

        if( notifications == null || listener == null ) {
            return;
        }

        if( LOG.isTraceEnabled() ) {
            LOG.trace( "{}: submitNotifications for listener {}: {}",
                       name, listener.toString(), notifications );
        }

        ListenerKey<L> key = new ListenerKey<>( listener );
        NotificationTask newNotificationTask = null;

        // Keep looping until we are either able to add a new NotificationTask or are able to
        // add our notifications to an existing NotificationTask. Eventually one or the other
        // will occur.

        try {
            while( true ) {
                NotificationTask existingTask = listenerCache.get( key );

                if( existingTask == null || !existingTask.submitNotifications( notifications ) ) {

                    // Either there's no existing task or we couldn't add our notifications to the
                    // existing one because it's in the process of exiting and removing itself from
                    // the cache. Either way try to put a new task in the cache. If we can't put
                    // then either the existing one is still there and hasn't removed itself quite
                    // yet or some other concurrent thread beat us to the put although this method
                    // shouldn't be called concurrently for the same listener as that would violate
                    // notification ordering. In any case loop back up and try again.

                    if( newNotificationTask == null ) {
                        newNotificationTask = new NotificationTask( key, notifications );
                    }

                    existingTask = listenerCache.putIfAbsent( key, newNotificationTask );
                    if( existingTask == null ) {

                        // We were able to put our new task - now submit it to the executor and
                        // we're done. If it throws a RejectedxecutionException, let that propagate
                        // to the caller.

                        LOG.debug( "{}: Submitting NotificationTask for listener {}",
                                   name, listener.toString() );

                        executor.execute( newNotificationTask );
                        break;
                    }
                } else {

                    // We were able to add our notifications to an existing task so we're done.

                    break;
                }
            }
        } catch( InterruptedException e ) {

            // We were interrupted trying to offer to the listener's queue. Somebody's probably
            // telling us to quit.

            LOG.debug( "{}: Interrupted trying to add to {} listener's queue",
                       name, listener.toString() );
        }

        if( LOG.isTraceEnabled() ) {
            LOG.trace( "{}: submitNotifications dine for listener {}",
                       name, listener.toString() );
        }
    }

    /**
     * Returns {@link ListenerNotificationQueueStats} instances for each current listener
     * notification task in progress.
     */
    public List<ListenerNotificationQueueStats> getListenerNotificationQueueStats() {
        List<ListenerNotificationQueueStats> statsList = new ArrayList<>( listenerCache.size() );
        for( NotificationTask task: listenerCache.values() ) {
            statsList.add( new ListenerNotificationQueueStats(
                    task.listenerKey.toString(), task.notificationQueue.size() ) );
        }

        return statsList ;
    }

    /**
     * Returns the maximum listener queue capacity.
     */
    public int getMaxQueueCapacity(){
        return maxQueueCapacity;
    }

    /**
     * Returns the {@link Executor} to used for notification tasks.
     */
    public Executor getExecutor(){
        return executor;
    }

    /**
     * Used as the listenerCache map key. We key by listener reference identity hashCode/equals.
     * Since we don't know anything about the listener class implementations and we're mixing
     * multiple listener class instances in the same map, this avoids any potential issue with an
     * equals implementation that just blindly casts the other Object to compare instead of checking
     * for instanceof.
     */
    private static class ListenerKey<L> {

        private final L listener;

        public ListenerKey( L listener ) {
            this.listener = listener;
        }

        L getListener() {
            return listener;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode( listener );
        }

        @Override
        public boolean equals( Object obj ) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ListenerKey<?>)) {
                return false;
            }

            ListenerKey<?> other = (ListenerKey<?>) obj;
            return listener == other.listener;
        }

        @Override
        public String toString() {
            return listener.toString();
        }
    }

    /**
     * Executor task for a single listener that queues notifications and sends them serially to the
     * listener.
     */
    private class NotificationTask implements Runnable {

        private final BlockingQueue<N> notificationQueue;

        private volatile boolean done = false;

        @GuardedBy("queuingLock")
        private boolean queuedNotifications = false;

        private final Lock queuingLock = new ReentrantLock();

        private final ListenerKey<L> listenerKey;

        NotificationTask( ListenerKey<L> listenerKey, Iterable<N> notifications ) {

            this.listenerKey = listenerKey;
            this.notificationQueue = new LinkedBlockingQueue<>( maxQueueCapacity );

            for( N notification: notifications ) {
                this.notificationQueue.add( notification );
            }
        }

        boolean submitNotifications( Iterable<N> notifications ) throws InterruptedException {

            queuingLock.lock();
            try {

                // Check the done flag - if true then #run is in the process of exiting so return
                // false to indicate such. Otherwise, offer the notifications to the queue.

                if( done ) {
                    return false;
                }

                for( N notification: notifications ) {

                    while( true ) {

                        // Try to offer for up to a minute and log a message if it times out.

                        // FIXME: we loop forever to guarantee delivery however this leaves it open
                        // for 1 rogue listener to bring everyone to a halt. Another option is to
                        // limit the tries and give up after a while and drop the notification.
                        // Given a reasonably large queue capacity and long timeout, if we still
                        // can't queue then most likely the listener is an unrecoverable state
                        // (deadlock or endless loop).

                        if( LOG.isDebugEnabled() ) {
                            LOG.debug( "{}: Offering notification to the queue for listener {}: {}",
                                       name, listenerKey.toString(), notification );
                        }

                        if( notificationQueue.offer( notification, 1, TimeUnit.MINUTES ) ) {
                            break;
                        }

                        LOG.warn(
                            "{}: Timed out trying to offer a notification to the queue for listener {}." +
                            "The queue has reached its capacity of {}",
                            name, listenerKey.toString(), maxQueueCapacity );
                    }
                }

                // Set the queuedNotifications flag to tell #run that we've just queued
                // notifications and not to exit yet, even if it thinks the queue is empty at this
                // point.

                queuedNotifications = true;

            } finally {
                queuingLock.unlock();
            }

            return true;
        }

        @Override
        public void run() {

            try {
                // Loop until we've dispatched all the notifications in the queue.

                while( true ) {

                    // Get the notification at the head of the queue, waiting a little bit for one
                    // to get offered.

                    N notification = notificationQueue.poll( 10, TimeUnit.MILLISECONDS );
                    if( notification == null ) {

                        // The queue is empty - try to get the queuingLock. If we can't get the lock
                        // then #submitNotifications is in the process of offering to the queue so
                        // we'll loop back up and poll the queue again.

                        if( queuingLock.tryLock() ) {
                            try {

                                // Check the queuedNotifications flag to see if #submitNotifications
                                // has offered new notification(s) to the queue. If so, loop back up
                                // and poll the queue again. Otherwise set done to true and exit.
                                // Once we set the done flag and unlock, calls to
                                // #submitNotifications will fail and a new task will be created.

                                if( !queuedNotifications ) {
                                    done = true;
                                    break;
                                }

                                // Clear the queuedNotifications flag so we'll try to exit the next
                                // time through the loop when the queue is empty.

                                queuedNotifications = false;

                            } finally {
                                queuingLock.unlock();
                            }
                        }
                    }

                    notifyListener( notification );
                }
            } catch( InterruptedException e ) {

                // The executor is probably shutting down so log as debug.
                LOG.debug( "{}: Interrupted trying to remove from {} listener's queue",
                           name, listenerKey.toString() );
            } finally {

                // We're exiting, gracefully or not - either way make sure we always remove
                // ourselves from the cache.

                listenerCache.remove( listenerKey );
            }
        }

        private void notifyListener( N notification ) {

            if( notification == null ) {
                return;
            }

            try {

                if( LOG.isDebugEnabled() ) {
                    LOG.debug( "{}: Invoking listener {} with notification: {}",
                               name, listenerKey.toString(), notification );
                }

                listenerInvoker.invokeListener( listenerKey.getListener(), notification );

            } catch( RuntimeException e ) {

                // We'll let a RuntimeException from the listener slide and keep sending any
                // remaining notifications.

                LOG.error( String.format( "%1$s: Error notifying listener %2$s", name,
                           listenerKey.toString() ), e );

            } catch( Error e ) {

                // A JVM Error is severe - best practice is to throw them up the chain. Set done to
                // true so no new notifications can be added to this task as we're about to bail.

                done = true;
                throw e;
            }
        }
    }
}
