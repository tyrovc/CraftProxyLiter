/*******************************************************************************
 * Copyright (C) 2012 Raphfrk
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.raphfrk.craftproxyliter;

public class ReconnectCache {

	private static MyPropertiesFile pf = null;
	private static String filename = null;
	private static SaveThread saveThread = null;

	static synchronized void init(String filename) {

		if( pf == null ) {
			ReconnectCache.filename = filename;
			pf = new MyPropertiesFile(filename);
			pf.load();
			if (saveThread != null) {
				saveThread.kill();
				saveThread = null;
			}
			saveThread = new SaveThread();
			saveThread.setPriority(Thread.MIN_PRIORITY);
			saveThread.start();
		}

	}
	
	static void killThread() {
		boolean first = true;
		SaveThread saveThreadLocal = null;
		while (first || saveThreadLocal != null) {
			first = false;
			saveThreadLocal = getAndClearSaveThread();
			if (saveThreadLocal != null) {
				saveThreadLocal.kill();
			}
		}
	}
	
	static private synchronized SaveThread getAndClearSaveThread() {
		SaveThread saveThreadLocal = saveThread;
		saveThread = null;
		return saveThreadLocal;
	}
	
	static synchronized void reload() {
		pf = null;
		init(filename);
	}

	static synchronized void store(String player, String hostname) {
		if(pf==null) return;
		pf.setString(player,hostname);

	}
	
	static synchronized boolean isSet() {
		return pf != null;
	}

	static synchronized String get(String player) {
		if(pf==null) return "";
		return pf.getString(player, "");
	}

	static synchronized void save() {
		if(pf==null) return;
		pf.save();
	}

	static synchronized void remove(String player) {
		if(pf==null) return;
		pf.removeRecord(player);
	}
	
	private static class SaveThread extends Thread {
		
		public void kill() {
			while (isAlive()) {
				interrupt();
				try {
					join(100);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}
		}
		
		public void run() {
			while (!isInterrupted()) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
				save();
			}
		}
		
	}


}
