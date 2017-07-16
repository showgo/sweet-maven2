package org.sweet.widget.commander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Commander
 * @filename:Commander
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2017年7月16日
 * @modifyrecords:
 */
public final class Commander {
	private List<String> commands=new ArrayList<String>();

	/**
	 * 构造函数
	 * @param commands
	 */
	private Commander(String...commands){
		if(null!=commands){
			for(String command:commands){
				this.command(command);
			}
		}
	}

	/**
	 * 返回Commander实例
	 * @return
	 */
	public static Commander getCommander(){
		return new Commander();
	}

	/**
	 * 返回Commander实例
	 * @param commands
	 * @return
	 */
	public static Commander getCommander(String...commands){
		return new Commander(commands);
	}

	/**
	 * 返回Commander实例
	 * @param commandList
	 * @return
	 */
	public static Commander getCommander(List<String> commandList){
		return null!=commandList ? new Commander(commandList.toArray(new String[commandList.size()])) : new Commander();
	}

	/**
	 * 添加命令
	 * @param cmd
	 * @return
	 */
	public Commander command(String cmd){
		this.commands.add("cmd.exe /c "+cmd);
		return this;
	}

	/**
	 * 同步执行命令(组)
	 */
	public void run(){
		/* 利用线程池实现多命令执行同步 */
		/* 利用CountDownLatch实现在当前线程(主线程)内启动其他子线程,保证所有子线程结束后才结束当前线程 */
		ExecutorService services=Executors.newFixedThreadPool(1);
		for(final String command:this.commands){
			services.execute(new Runnable() {
				Process process=null;

				public void run(){
					try{
						System.out.println(command);
						process=Runtime.getRuntime().exec(command);
						CountDownLatch latch=new CountDownLatch(2);
						new Thread(new CommandResult(latch,process.getInputStream())).start();
						new Thread(new CommandResult(latch,process.getErrorStream())).start();
						latch.await();
					}catch(IOException ex){
						ex.printStackTrace();
					}catch(InterruptedException e){
						e.printStackTrace();
					}finally{
						try{
							process.getOutputStream().close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}
			});
		}
		services.shutdown();
	}

	/**
	 * 异步执行命令(组)
	 */
	public void runAsync(){
		for(String command:commands){
			Process process=null;
			try{
				System.out.println(command);
				process=Runtime.getRuntime().exec(command);
				new Thread(new CommandResult(process.getInputStream())).start();
				new Thread(new CommandResult(process.getErrorStream())).start();
			}catch(IOException ex){
				ex.printStackTrace();
			}finally{
				try{
					process.getOutputStream().close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 */
	static class CommandResult implements Runnable {
		private CountDownLatch latch;
		private InputStream stream;

		/**
		 * 构造函数
		 * @param stream
		 */
		public CommandResult(InputStream stream){
			this.latch=null;
			this.stream=stream;
		}

		/**
		 * 构造函数
		 * @param latch
		 * @param stream
		 */
		public CommandResult(CountDownLatch latch,InputStream stream){
			this.latch=null!=latch ? latch : null;
			this.stream=stream;
		}

		public void run(){
			try{
				BufferedReader reader=new BufferedReader(new InputStreamReader(stream,"utf-8"));
				String line=null;
				while((line=reader.readLine())!=null){
					System.out.println(line);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(null!=latch){
					this.latch.countDown();
				}
			}
		}
	}
}
