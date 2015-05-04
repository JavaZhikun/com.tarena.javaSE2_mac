package day01;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;//接口中定义接口
import java.util.Set;

import org.junit.Test;

public class TestCase {
	
	
	//@Test
	public void testPM25(){
		//中英文冒号和逗号不要混用
	 String pm25 = "海淀:50,亚运村:30,门头沟:40,崇文门:200,西单:230,海淀:180,亚运村:100";	
	 //劈开数据
	 String[] data = pm25.split("[:,]");
	 //将数据添加到散列表中，进行统计
	 Map<String, Integer> map = new LinkedHashMap<String, Integer>();
	 //每次添加两个
	 for(int i =0; i < data.length; i+= 2){
		 //i = 0,2,4,6,8  检查地点index
		 //data[i] 就是检查地点location
		 String location = data[i];
		 int value = Integer.parseInt(data[i+1]);
		 Integer v =  map.get(location);//取对象
//		 if(v == null){//之前没有添加
//			 map.put(location, value);
//		 }else{//已有相同的地址
//			 map.put(location,Math.max(value, v));//将最大值弄进去
//		 }
		 
		 map.put(location, v==null?value:Math.max(value, v)); 
	 }
	 System.out.println(map);
	 
	 //使用key进行迭代
	 //keys就是全部key的集合，<String>是key泛型
	 Set<String> keys = map.keySet();
	 for( String key:keys){
		 int val = map.get(key);
		 System.out.println(key+":"+val);
	 }
	 
	 System.out.println("---------");
	 //使用key-value对进行迭代
	 //Set的每个元素都是Entry，Entry中包含key和value
	 // <String,Integer>分别是key，value的泛型
	 Set<Entry<String,Integer>> entries = map.entrySet();
	 for(Entry<String,Integer> entry : entries){
		String  key =  entry.getKey();
		 int val =  entry.getValue();
		 System.out.println(key + ":" + val);
	 }
	}
	 
	 /**
	  * 路径的分隔符现象：
	  */
	 //@Test
	 public void testFile(){
	 	 //File.separator  值会随操作系统变化
	 	 //Windows：\   Linux/Unix： /
	 	 String filename ="demo" + File.separator +"test.txt";
	 	 System.out.println(filename);
        //如下是java中的通用写法，java会自动适应不同的操作系统
	 	 String file = "demo/test.txt";
	 }
	 
	 /**
	  * File可以代表文件，也可以代表目录
	  * File 类有3个重载的构造器
	  *        new  File（"文件夹、目录"）
	  *        new File("父目录"，"文件夹、目录")
	  *        new File（File 父目录，"文件夹、目录"）
	  */
	// @Test
	 public void testFile12(){
		 File dir = new File("C:/windows");
		 //File dir = new File("/etc");
		 File file = new File("D:/erroe.log");
		 //File file = new File("/etc/passwd");
		 //                    父目录    子目录
		 File dir2 = new File("C/","windows");
		 //File dir2 = new File("/","etc");
		 //目录  == 文件夹
		 //Linux写法
		
		 System.out.println(dir2);
		 File file2 = new File(dir,"error.log");
		 System.out.println(file2);
		 //
	 }
	 
	 /**
	  * 检查file对象指向的是否是文件
	  */
	 //@Test 
	 public void testIsFile(){
		 File file1 =  new File("/etc");
		 //在linux上有文件/etc/passwd
		 File file2 =  new File("/etc/passwd");
		 System.out.println(file1.isFile());//false
		 System.out.println(file2.isFile());//true
		 //length（）方法可用于检查文件的长度
		 //返回值是long类型，是文件中的字节（Byte）的个数。
		 System.out.println(file2.length());//5253
	 }
	 
	 /**
	  * 检查文件或者文件夹是否已经存在
	  */
	 //@Test
	 public void testExist(){
		 //new File（）只是创建内存对象
		 //不代表磁盘上使用有对应的文件，文件夹
		 //更不是直接在磁盘上创建了文件
		 File file = new File("/cte");
		 System.out.println(file.isFile());
		 System.out.println(file.length());
		 //检查file对象指向的磁盘上使用存在文件，文件夹
		 System.out.println(file.exists());
		 //在磁盘上不存在/cte的文件，文件夹
		 //内存中有file对象，但磁盘上没有这个文件夹或文件
	 }
	 
	 /**
	  * 创建新闻文件
	 * @throws IOException 
	  */
	// @Test
	 public void testCreateNewFile() throws IOException{
		 File file = new File("/Users/luanzhikun","demo.txt");
		 System.out.println(file.exists());//false
		 //在磁盘上创建文件，成功返回true
		 boolean ok = file.createNewFile();//true
		 System.out.println(ok);//
		 //再次创建文件，不会成功，返回false
		 ok = file.createNewFile();
		 System.out.println(ok);//false
		 
		 //无权限情况，方法执行异常
		 File file1 = new File("/etc","demo.txt");
		 ok = file1.createNewFile();//抛出异常，没有权限
		 System.out.println(ok);
		 
	 }
	 
	 
	 /**
	  * 列文件夹的目录
	  */
	 //@Test
	 public void testListFiles(){
		 //创建一个file对象，这个对象对应一个目录
		 File dir = new File("/Users/luanzhikun/desktop");
		 //执行列目录方法
		 File[] files = dir.listFiles();
		 //files 里面有文件或文件夹
		 for(File file:files){
			 //file可能是文件，也可能是文件夹
			 //文件夹用[文件夹]  文件直接输出
			 if(file.isDirectory()){
				 //getName()返回文件夹名
				 System.out.println("[" + file.getName() +"]");
			 }else{
				 System.out.println(file.getName());
			 }
		 }
	 }
	 
	 //
	 
	 //@Test 
	 public void  test1(){
		 String line =  "good good study,day day up.";
		 line = line.replaceAll("[^a-zA-Z]+","");//将非字母的全部替换成为空字符
		 Map<Character,Integer> map = new HashMap<Character,Integer>();
		 for(int i = 0; i < line.length();i++){
			char key = line.charAt(i);
			int number = map.get(key);
			map.put(key, number==0?1:++number); 
		 }
		 
		 Set<Character> keys = map.keySet();
		 for(Character key: keys){
			int value = map.get(key);
			System.out.println(key+":"+value+" ");
			 
		 }
	 }
	 
	 
	 /**
	  * 过滤目录内容
	  * 如：只列图片内容
	  *   列文本文件
	  *   列java文件
	  *   
	  *   
	  *   java提供重载listFiles（过滤器）
	  *     工作原理：列出目录的每个文件，将每个文件交给过滤器检查，过滤器检查
	  *            通过的文件保留下来作为最后的结果
	  *            
	  *   过滤器：保留、java文件:
	  *   1.创建一个过滤器，给定过滤条件
	  *   2.创建file对象，指向一个文件夹
	  *   3.在file对象上执行
	  *       File[] files = file.listFiles(过滤器)
	  *   4.返回结果就是满足过滤器条件的结果Ø
	  */
	 //@Test
	 public void testFileFilter(){
		 //创建过滤器  java.io.FileFilter
		 //Filter过滤器
		 FileFilter filter = new FileFilter(){
			 //重写Filter提供的过滤规则方法
			 //accept 接受
			 //过滤器工作的时候，listFiles方法将
			 //文件夹中每个文件传递给pathname
			 //方法返回值是true表示接受当前这个
			 //pathname对应的文件
			 //甚至可以使用matches（regex）
			@Override
			public boolean accept(File pathname) {
				//写复杂逻辑
				//return pathname.isFile() &&...
				return pathname.getName().endsWith(".png");
			}};
			
			File file = new File("/Users/luanzhikun/desktop");//桌面上的.png文件
			
			//listFiles 方法在/User/luanzhikun/desktop使用过滤器列目录
			//只保留满足条件的文件
			
			//只要文件，不要文件夹  listFile
			File[] files = file.listFiles(filter);
			for(File f:files){
				//如果是f，则列出全名，如果是f.getName（）则之列出文件名
				System.out.println(f.getName());
			}
	 }
	 
	// @Test
	 public void testListFile2(){
		 File file = new File("/Users/luanzhikun/desktop");
		 //在过滤器变量位置上直接写匿名内部类
		 //上班都这么写
		 File[] files = file.listFiles(
				 new FileFilter(){
					 public boolean accept(File f){
						 return f.getName().endsWith(".png");
					 }
		 });
		 for(File f: files){
			 System.out.println(f.getName());
		 }
	 }
	 
	 /**
	  * RandomAccessFile
	 * @throws IOException 
	  */
	 
	// @Test
	 public void testRandomAccessFile() throws IOException{
		 RandomAccessFile raf = new RandomAccessFile("demo.dat","rw");
		 //只会把最后一个byte(低8位)写进文件里面。
		 //write 方法将参数int类型的低8位（1个byte）写入到文件中
		 raf.write(0x00000041);
		 raf.write(0x42);//写入第二个byte
		 //文件用完务必关闭。
		 raf.close();
	 }
	 
	 /**
	  * 读文件，当文件打开的时候，游标还是从0开始
	  * java会进行低8为填充，将低8位读取出来对int类型的低8位进行填充。
	  * Integer.toHexString(b) 十六进制输出
	  * 
	  * 当读取到文件末尾时，返回0xffffffff表示结束
	  */
	 
	 /**
	  * 读取一个文件
	 * @throws IOException 
	  */
	 //@Test
	 public void testReadFile() throws IOException{
		 //打开文件
		 RandomAccessFile raf = new RandomAccessFile("demo.dat","r");
		 //读取第一个byte
		 int b = raf.read();
		 System.out.println(Integer.toHexString(b));
		 //读取第二个byte
		 b = raf.read();
		 System.out.println(Integer.toHexString(b));
		//读取第三个byte，已经到了文件末尾
		 b = raf.read();
		 System.out.println(Integer.toHexString(b));
		 raf.close();
		 
	 }
	 
	 /**
	  * 读取文件的经典写法
	  */
	 //@Test
	 public void testReadFile2() throws Exception{
		 RandomAccessFile raf = new RandomAccessFile("demo.dat","r");
		 //读取文件的经典写法
		 //demo.dat = 41   42
		 //           ^
		 int b ;//
		 //        0xffffffff = -1.(补码为正数的原码取反加一)
		 //方法返回结果返回 41  
		 while((b= raf.read())!=-1){
			 System.out.println(Integer.toHexString(b));
		 }
		 raf.close();
	 }
	 
	 //重载的write方法
	 
	 /**
	  * 成批的将byte数组的数据写入到文件中
	 * @throws Exception 
	  */
	 
	 @Test
	 public void testWriteBytes() throws Exception{
		 RandomAccessFile raf = new RandomAccessFile("test.dat","rw");
		 byte[] buf = {0x41,0x42,0x43,(byte)0xe4,(byte)0xb8,(byte)0xad};
		 //将buffer中位置0开始连续写出6个byte write（byte[],int offset,int len）
		 raf.write(buf,0,6);
         //raf.write(buf);  此句和上面的是一样的
		 raf.close();
		 
		 raf = new RandomAccessFile("test.dat","r");
		 int b;
		 while(( b = raf.read())!= -1){
			 System.out.println(Integer.toHexString(b));
		 }
		 raf.close();
		 
	 }	 
	 
	 
	 
	 //@Test
	 public void testFileFilter2(){
		 File file = new File("/Users/luanzhikun/desktop/大数据");
		 File[] files = file.listFiles(new FileFilter(){

			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".pdf");
				
			}
		 });
		 
		 for(File f : files){
			 System.out.println(f.getName());
		 }
	 }
	 
	 
	 
	 
}
	 
	 
	 
	 
	 
	

