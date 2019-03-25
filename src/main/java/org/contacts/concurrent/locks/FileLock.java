package org.contacts.concurrent.locks;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class FileLock implements Lock {

	private static final int DEFAULT_SLEEP = 500;
	
	private File file;

	public FileLock(File file) {
		this.file = file;
	}

	
	@Override
	public void lock() {
		while(!file.mkdir());
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		while(!file.mkdir()) {
			Thread.sleep(DEFAULT_SLEEP);
		}
	}

	@Override
	public boolean tryLock() {
		return file.mkdir();
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return tryLock() || dotryLock(time, unit);
	}
	
	private boolean dotryLock(long time, TimeUnit unit) {
		if(time <= 0L) {
			return false;
		}
		long nanos = unit.toNanos(time);
		long deadline = System.nanoTime() + nanos;
		long difference = deadline;
		do {
			boolean isLocked = tryLock();
			if(isLocked) {
				return true;
			} else {
				difference -= System.nanoTime();
			}
		} while(difference <= 0);
		return false;
	}

	@Override
	public void unlock() {
		file.delete();
	}

	@Override
	public Condition newCondition() {
		return null;
	}

}
