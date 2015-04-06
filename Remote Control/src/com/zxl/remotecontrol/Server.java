package com.zxl.remotecontrol;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static final int PORT = 9695;
	private ServerSocket ss = null;
	private Socket socket = null;
	private InputStream is = null;
	private ObjectInputStream ois = null;
	private Robot robot = null;
	private int pcX = 0;
	private int pcY = 0;

	public static void main(String[] args) {
		Server server = new Server();
		server.mainThread();
	}

	private void mainThread() {
		try {
			ss = new ServerSocket(PORT);
			while (true) {
				socket = ss.accept();
				is = socket.getInputStream();
				ois = new ObjectInputStream(is);
				Object o = ois.readObject();
				
				if (o instanceof Move) {
					move((Move) o);
				}
				if (o instanceof Click) {
					click((Click) o);
				}
				if (o instanceof RightClick) {
					rightClick((RightClick) o);
				}
				if (o instanceof Down) {
					down((Down) o);
				}
				if (o instanceof Up) {
					up((Up) o);
				}
				if (o instanceof Scroll) {
					scroll((Scroll) o);
				}
				if (o instanceof ScrollCancelled) {
					scrollCancelled((ScrollCancelled) o);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != ois) {
					ois.close();
				}
				if (null != is) {
					is.close();
				}
				if (null != ss) {
					ss.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void scrollCancelled(ScrollCancelled o) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.mouseRelease(KeyEvent.BUTTON2_MASK);
	}

	private void scroll(Scroll o) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.mousePress(KeyEvent.BUTTON2_MASK);
	}

	private void down(Down o) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.mousePress(KeyEvent.BUTTON1_MASK);
	}

	private void up(Up o) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.mouseRelease(KeyEvent.BUTTON1_MASK);
	}

	private void rightClick(RightClick o) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.mousePress(KeyEvent.BUTTON3_MASK);
		robot.mouseRelease(KeyEvent.BUTTON3_MASK);
	}

	private void click(Click o) {
		robot.mousePress(KeyEvent.BUTTON1_MASK);
		robot.mouseRelease(KeyEvent.BUTTON1_MASK);
	}

	private void move(Move move) {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		Point pointer = pointerInfo.getLocation();
		pcX = pointer.x;
		pcY = pointer.y;
		pcX = (int) (pcX + (int) move.getX());
		pcY = (int) (pcY + (int) move.getY());
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.mouseMove(pcX, pcY);
	}

}
