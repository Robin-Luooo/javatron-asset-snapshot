package org.tron.core.db.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.core.capsule.BlockCapsule;
import org.tron.core.db.Manager;


@Component
public class AccountExportUtil {

  private Manager manager;

  @Autowired
  public AccountExportUtil(Manager manager) {
    this.manager = manager;
  }

  @Autowired
  private AccountExporter exporter;

  public void doExport(long height, BlockCapsule blockCapsule, long startBlockHeight) {
    exporter.export(height, this.manager.getAccountStore(), blockCapsule, this.manager.getTransactionRetStore(), this.manager.getBlockIndexStore(),startBlockHeight);
  }

  public void doExport0301(long height, BlockCapsule blockCapsule, long startBlockHeight, String token) {
    exporter.export0301(height, this.manager.getAccountStore(), blockCapsule, this.manager.getTransactionRetStore(), this.manager.getBlockIndexStore(),startBlockHeight, token);
  }
}