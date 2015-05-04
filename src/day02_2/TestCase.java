package day02_2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.junit.Test;

public class TestCase {
	
	/**
	 * 批量写数据
	 * 准备一批数据byte[] ,将一批数据写到文件中
	 * @throws Exception 
	 */
	//@Test
	public void testWriteFile() throws Exception{
		byte[] bytes = {1,2,3,4,5,127};
		//在"rw"模式的时候，如果磁盘上面没有文件，
		//test.dat 则自动在磁盘上创建文件
		RandomAccessFile raf = new RandomAccessFile("test.dat","rw");
		//打开以后raf的指针读取位置在0
		//write方法将数组中全部byte数据，依次写入到打开的文件中，
		// 文件指针自动移动n次
		raf.write(bytes);
		//write 成批写入，将数据byte[]从0 位置开始
		//连续写6个数据，文件指针连续移动6次
		raf.write(bytes,0,6);
		raf.close();
	}
	
	/**
	 * 成批的读取文件中的数据
	 * @throws Exception 
	 */
	//@Test
	public void testReadFile() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.dat","r");
		//文件打开时，指针位置在0
		//成批的读取数据，需要读取到一个byte数组中
		//先创建此byte数组
		byte[] buf = new byte[6];//比文件长度短
		//read（buf） 从文件中尽可能多的读取数据到数组中，文件是12个byte
		// 数组是6个byte。读取6个byte填充到数组，文件指针移动6次
		//返回值是读取数据的个数6
		int n = raf.read(buf);
		System.out.println(n);
		System.out.println(Arrays.toString(buf));
		raf.close();
		
	}
	
	/**
	 * 批量读取文件
	 * @throws Exception 
	 */
	//@Test
	public void testReadFile2() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.dat","r");
		byte[] buf = new byte[20];
		//byte 数组大于文件的长度
		//read方法在读取数据的时候，讲文件中的数据读取到数组中，读取了12个数据，返回值12
		//表示读取12个数据，数据没有被填满，数组有效的数据是0——11，合计为12
		int n = raf.read(buf);
		System.out.println(n);
		System.out.println(Arrays.toString(buf));
		raf.close();
	}
	
	/**
	 * read 经典用法： 一次性读取文件的全部内容到数组中
	 * @throws Exception 
	 */
	
	//@Test
	public void testFileContent() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.dat","r");
		//raf.length() 获取文件的长度long类型
		//buf数组的长度与文件的长度一致，但是这个语句要小心
		//文件长度不能过大！
		byte[] buf = new byte[(int)raf.length()];
		int n = raf.read(buf);
		raf.close();
		System.out.println(n+":"+Arrays.toString(buf));//文件中的全部数据
		
	}
	
	/**
	 * 批次读取文件内容
	 */
	//@Test
	public void testRead() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.dat","r");
		byte[] buf = new byte[5];
		int n = raf.read(buf);
		System.out.println(n+":"+ Arrays.toString(buf));
		
		n = raf.read(buf);
		System.out.println(n+":"+Arrays.toString(buf));
		
		//连续读取2个数据，返回2
		n = raf.read(buf);
		System.out.println(n +":"+ Arrays.toString(buf));
		raf.close();
	}
	
	/**
	 * 文件指针的操作
	 * @throws Exception 
	 */
	//@Test
	 public  void testFilePointer() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("test.dat","rw");
		//打开文件的时候，指针指在0位置
		//获取raf的当前文件指针位置
		//point指向当前文件指针位置
		long pointer = raf.getFilePointer();
		System.out.println(pointer);
		int b = raf.read();
		pointer = raf.getFilePointer();
		System.out.println(pointer);
		//有两种方法移动文件指针
		//A 绝对移动seek(0)  移动到0位置
		raf.seek(0);
		pointer = raf.getFilePointer();
		System.out.println(pointer);
		
		//B 相对移动skipBytes（1）向后移动一个
		raf.skipBytes(5);
		pointer = raf.getFilePointer();
		System.out.println(pointer);//5
	}
	
	/**
	 *   文件指针的工作
	 *   
	 *   1.打开文件时候文件指针在开始位置0
	 *   2.每次读写，读写了几个byte，文件指针就移动几个byte
	 *   3.如果是连续读写，是不需要干预文件指针的位置的
	 *   4.如果不是连续读写，就需要调整指针位置了
	 */
	
	/**
	 * 如何将一个整数写入到文件
	 * 
	 * raf.writeInt（int）将参数int拆分为4个byte
	 * 数据，连续写入到文件中，文件指针自动移动4次
	 * raf.write(max) //写入低8位一个byte数据
	 * @throws Exception 
	 */
	
	//@Test
	public void testWriteInt() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("num.dat","rw");
		raf.writeInt(Integer.MAX_VALUE);
		System.out.println(raf.getFilePointer());//4
		raf.writeLong(Long.MAX_VALUE);
		System.out.println(raf.getFilePointer());//12
	}
	
	/**
	 * num.dat 文件中的数据
	 * 1.从0开始连续4个byte是int数据
	 * 2.从4开始连续8个byte是long数据
	 * 。。。。。
	 * 读取数据，从当前指针开始，位置极其重要
	 * @throws Exception 
	 */
	//@Test
	public void testReadLong() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("num.dat","r");
		raf.seek(4);
		long l = raf.readLong();//连续读取8个byte
		System.out.println(l);
		raf.seek(0);
		int i = raf.readInt();
		System.out.println(i);
		raf.close();

	}
	
	/**
	 * 1.文件的基本访问规则，每次读写1个byte
	 * 2.int long  double 等数据需要拆分为byte写到文件中
	 * 3.读取时候，需要读取多个byte合并为int，long，double
	 * 4.java提供了int long等数据的读写API
	 * 5.对象需要拆分为byte序列写到文件中
	 * 6.字符串也需要产分为byte写到文件中
	 * 如何将字符串拆分为byte序列：
	 *    "abc中国".getBytes("UTF-8");
	 * @throws Exception 
	 */
	//@Test
	public void testString() throws Exception{
		String str ="你好达内！";
		//将字符串按照UTF-8标准编码为byte序列
		byte[] bytes = str.getBytes("UTF-8");
		RandomAccessFile raf = new RandomAccessFile("str.dat","rw");
		//写数组到文件中，是写字符串的byte数据
		//到文件中。本案例写了15个byte(中文字符的UTF-8对应3个byte)
		//文件从0开始连续15个bytes存储的是String
		raf.write(bytes);
		raf.seek(0);//返回0
		byte[] buf = new byte[15];
		raf.read(buf);
		String s = new String(buf,"utf-8");
		System.out.println(s);
		raf.close();
	}
	
	
	/**
	 * 业务：将电话本存在一个文件中
	 * 编写算法： 1.存储电话本联系人到一个文件中
	 * 			2.读取电话本文件打印联系人
	 * 
	 * 电话本中的联系人是对象
	 * 联系人： 编号（int），姓名（String）
	 * 	      电话（String） QQ（long）
	 * 
	 * 方法1： 存储联系人
	 *     文件中每372 个byte为一个单元（记录）
	 *     存储一条联系人信息（存储一个对象）
	 *     
	 * 方法2 ：读取联系人
	 */
	
	/**
	 * 添加一个将联系人写到文件的方法
	 * raf 是已经打开的文件
	 * obj 联系人信息
	 * rowId  是行号位置
	 * 定长（372）文件的标准输出方法
	 * @throws Exception 
	 */
	
	public  static void writeContact(RandomAccessFile raf, Contact obj, int rowId) throws Exception{
		long start = rowId * 372;
		//写Id到文件中
		raf.seek(start + 0);
		raf.writeInt(obj.getId());
		
		//写name到文件中
		raf.seek(start + 4);//int : 4
		raf.write(obj.getName().getBytes("UTF-8"));//30
		
		//写phone到文件中
		raf.seek(start + 34);
		raf.write(obj.getPhone().getBytes("UTF-8"));//20
		
		//写address到文件中
		raf.seek(start + 54);
		raf.write(obj.getAddress().getBytes("UTF-8"));//150
		
		//写入QQ
		raf.seek(start + 204);
		raf.writeLong(obj.getQQ());
		
		//检查是否是追加记录
		if(raf.getFilePointer() == raf.length()){
			// 正在文件尾部增加新的联系人信息
			raf.setLength(start + 372);//修改文件的长度
			
		}
		
		
	}
	
	/**
	 * 从文件中读取联系人信息
	 *   raf 保存联系人信息的文件
	 *   rowId 行号 0，1，2，3，4，。。。
	 *   return 联系人信息对象
	 */
	public static Contact readContact(RandomAccessFile raf, int rowId)throws Exception{
		long start = rowId * 372L;
		raf.seek(start+0);
		int id = raf.readInt();
		
		raf.seek(start+4);
		byte[] buf = new byte[30];
		raf.read(buf);//从4开始连续读取30个byte
		String name= new String(buf,"utf-8").trim();
		
		raf.seek(start+34);
		buf = new byte[20];
		raf.read(buf);
		String phone = new String(buf,"utf-8").trim();
		
		raf.seek(start+54);
		buf = new byte[150];
		raf.read(buf);
		String address = new String(buf,"utf-8").trim();
		
		raf.seek(start+204);
		long qq = raf.readLong();
		
		return new Contact(id, name, phone, address, qq);
				
		
	}
	
	/**
	 * 测试联系人信息读写
	 * @throws Exception 
	 */
	
	@Test
	public void testContact() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("contact.dat","rw");
		Contact tom = new Contact(1,"tom", "110","北京",12901);
		Contact jerry = new Contact(2,"jerry","119","海南",12345);
		
		// 将联系人写到文件中
		writeContact(raf, tom, 0);
		writeContact(raf,jerry,1);
		
		//从文件raf读取联系人信息到Contact对象
		Contact c1 = readContact(raf, 0);
		Contact c2 = readContact(raf,1);
		System.out.println(c1);
		System.out.println(c2);
		raf.close();
		
	}
	
	/**
	 * 实现文件的复制功能
	 * 利用FileInputStream 读文件
	 * 利用FileOutputStream 写文件
	 * @throws Exception 
	 */
	@Test
	public void testCopy() throws Exception{
		FileInputStream in = new FileInputStream("/Users/luanzhikun/desktop/file/1.txt");
		FileOutputStream out = new FileOutputStream("/Users/luanzhikun/desktop/file/2.txt");
		
		int b;//b是byte的缩写
		//从输入流in中读文件的每个byte，写到输出流out中
		//简单说，就是读一个byte，写一个byte
		while((b =  in.read())!= -1){
			out.write(b);
		}
		out.close();
		in.close();
		System.out.println("复制ok");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
		

}
