package org.tron.core.db.export;

import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.core.capsule.BlockCapsule;


@Slf4j(topic = "exporter")
@Component
@Aspect
public class AccountExportAspect {

  public static final AtomicLong EXPORT_NUM = new AtomicLong(Long.parseLong("38985700"));
  public static final AtomicLong EXPORT_TIME = new AtomicLong(Long.parseLong("1646064000000"));
  public static final AtomicLong START_BLOCK_HEIGHT = new AtomicLong(0);
  private BlockCapsule currBlock;

  @Autowired
  private AccountExportUtil util;

  @Pointcut("execution(** org.tron.core.db.Manager.pushBlock(..)) && args(block)")
  public void pointPushBlock(BlockCapsule block) {

  }

  public void exportAccount0301(String token) {
    if (currBlock == null) {
      System.out.println(" >>> block not reach, try latter");
      return;
    }
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          System.out.println(" >>> manually export data start, block=" + currBlock.getNum());
          util.doExport0301(currBlock.getNum(), currBlock, START_BLOCK_HEIGHT.get(), token);
        } catch (Exception ex) {
          logger.error("manually export account failure: token={}", token, ex);
        }
      }
    }).run();
  }

  @After("pointPushBlock(block)")
  public void exportAccount(BlockCapsule block) {

    if (block.getNum() == EXPORT_NUM.get() || Math.abs(block.getTimeStamp() - EXPORT_TIME.get()) <= 6000) {
      currBlock = block;
      while (true) {
        logger.info("export account block={}, sleeping... now={}", block.getNum(), System.currentTimeMillis());
        try {
          Thread.sleep(1200 * 1000);
        } catch (InterruptedException ex) {
          break;
        }
      }

//      try {
//        util.doExport(block.getNum(), block, START_BLOCK_HEIGHT.get());
//      } catch (Exception ex) {
//        logger.error("export account failure: {} {}", ex.getMessage(), ex.getStackTrace());
//      }
//      finally {
//        EXPORT_NUM.set(Long.parseLong("38985700"));
//        EXPORT_TIME.set(Long.parseLong("1646064000000"));
//      }
    }
  }

  @AfterThrowing("execution(** org.tron.core.db.Manager.pushBlock(..)) && args(block)")
  public void logException(BlockCapsule block) {

  }

}

