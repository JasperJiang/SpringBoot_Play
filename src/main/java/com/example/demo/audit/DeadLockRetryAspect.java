//package com.example.demo.audit;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.hibernate.exception.LockAcquisitionException;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//
///**
// * Add the logging aspect to record the method entry & method exit on all the
// * classes in the package
// */
//
//@Aspect
//@Component
//@Slf4j
//@Getter
//@Setter
//public class DeadLockRetryAspect implements Ordered {
//
//
//    /**
//     * Aspect order
//     **/
//    private int order = 99;
//
//    /**
//     * No of retries
//     **/
//    private int retryCount = 3;
//
//    /**
//     * delay count
//     **/
//    private int delay = 1000;
//
//    @Around(value = "@annotation(org.springframework.transaction.annotation.Transactional)")
//    public Object methodLevelRetry(ProceedingJoinPoint pjp) throws Throwable {
//        return detectDeadlocks(pjp);
//    }
//
//    @Around(value = "@within(org.springframework.transaction.annotation.Transactional)")
//    public Object classLevelRetry(ProceedingJoinPoint pjp) throws Throwable {
//        return detectDeadlocks(pjp);
//    }
//
//    private Object detectDeadlocks(ProceedingJoinPoint pjp) throws Throwable {
//        boolean isActualTXActive = TransactionSynchronizationManager.isActualTransactionActive();
//        if (log.isTraceEnabled()) {
//            log.trace("pointcut {} active TX Mgr: {}",
//                    pjp.toString(), isActualTXActive);
//        }
//
//        try {
//            int retries = getRetryCount();
//            while (true) {
//                try {
//                    return pjp.proceed();
//                } catch (LockAcquisitionException ex) {
//
//                    if (isActualTXActive) {
//                        if (log.isTraceEnabled())
//                            log.trace("Deadlock pointcut detected, but tx is still active - propagating");
//                        throw ex;
//                    } else {
//                        if (retries-- == 0) {
//                            throw ex;
//                        }
//
//                        if (log.isDebugEnabled()) {
//                            log.debug("Deadlock retryCount={} (sleeping {} ms)", retryCount, getDelay());
//                        }
//
//                        Thread.sleep(getDelay());
//                    }
//                }
//            }
//        } finally {
//            if (log.isTraceEnabled())
//                log.trace("Tx mgr : {}", pjp.toString(), isActualTXActive);
//        }
//    }
//
//    @Override
//    public int getOrder() {
//        return order;
//    }
//}