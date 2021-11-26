# PDF Compare Utility

MVN Dependency:
===============

```
<dependency>
   <groupId>com.testautomationguru.pdfutil</groupId>
   <artifactId>pdf-util</artifactId>
   <version>0.0.2</version>
</dependency>
```


# RepairHistory
1.修复差异内容不对，字体重影问题
2.增加输出全部比对图片方法
3.增加图片合并功能

# maven configure
问题1: idea控制台打包,javadoc乱码?
解决:在IDEA中，打开File | Settings | Build, Execution, Deployment | Build Tools | Maven | Runner在VM Options中添加-Dfile.encoding=GBK，切记一定是GBK。即使用UTF-8的话，依然是乱码，这是因为Maven的默认平台编码是GBK

# Usage

* To get page count
* 获取PDF页数

```
import com.testautomationguru.utility.PDFUtil;
 
PDFUtil pdfUtil = new PDFUtil();
pdfUtil.getPageCount("c:/sample.pdf"); //returns the page count
```

* To get page content as plain text
* 以纯文本的方式获取页面内容

```
//returns the pdf content - all pages
//以Text返回PDF的内容 - 所有页
pdfUtil.getText("c:/sample.pdf");
 
// returns the pdf content from page number 2
// 以Text返回PDF第2页的内容
pdfUtil.getText("c:/sample.pdf",2);
 
// returns the pdf content from page number 5 to 8
// 以Text返回PDF第5~8页的内容
pdfUtil.getText("c:/sample.pdf", 5, 8);

```

* To extract attached images from PDF
* 从PDF中提取附加图像
```
//set the path where we need to store the images
//设置我们需要存储图像的路径
 pdfUtil.setImageDestinationPath("c:/imgpath");
 pdfUtil.extractImages("c:/sample.pdf");
 
// extracts &amp; saves the pdf content from page number 3
// 从PDF的第3页中提取并保存内容
pdfUtil.extractImages("c:/sample.pdf", 3);
 
// extracts &amp; saves the pdf content from page 2
// 从PDF的第2页中提取并保存内容
pdfUtil.extractImages("c:/sample.pdf", 2, 2);

```


* To store PDF pages as images
* 将PDF页面存储为图像

```
//set the path where we need to store the images
//设置我们需要存储图像的路径
 pdfUtil.setImageDestinationPath("c:/imgpath");
 pdfUtil.savePdfAsImage("c:/sample.pdf");
```

* To compare PDF files in text mode (faster – But it does not compare the format, images etc in the PDF)
* 以文本模式比较PDF文件(速度更快-但不比较PDF中的格式、图像等)

```
String file1="c:/files/doc1.pdf";
String file1="c:/files/doc2.pdf";
 
// compares the pdf documents &amp; returns a boolean
// true if both files have same content. false otherwise.
// 比较PDF文档并返回 True or False
pdfUtil.compare(file1, file2);
 
// compare the 3rd page alone
// 仅比较第3页
pdfUtil.compare(file1, file2, 3, 3);
 
// compare the pages from 1 to 5
// 比较第1~5页
pdfUtil.compare(file1, file2, 1, 5);
```
* To exclude certain text while comparing PDF files in text mode
* 在文本模式下排除某些文本再对PDF文件进行比较

```
String file1="c:/files/doc1.pdf";
String file1="c:/files/doc2.pdf";
 
//pass all the possible texts to be removed before comparing
//对比之前删除有可能的文本内容
pdfutil.excludeText("1998", "testautomation");
 
//pass regex patterns to be removed before comparing
//使用正则表达式，在比较之前删除指定内容
// \\d+ removes all the numbers in the pdf before comparing
// \\d+ 在比较之前删除PDF中的所有数字 \\d+是数字的正则表达式
pdfutil.excludeText("\\d+");
 
// compares the pdf documents &amp; returns a boolean
// true if both files have same content. false otherwise.
// 比较PDF文档并返回一个布尔值
// True表示相同；false 表示不一样.
pdfUtil.compare(file1, file2);
 
// compare the 3rd page alone
// 仅比较第3页
pdfUtil.compare(file1, file2, 3, 3);
 
// compare the pages from 1 to 5
// 比较第1~5页
pdfUtil.compare(file1, file2, 1, 5);
```
* To compare PDF files in Visual mode (slower – compares PDF documents pixel by pixel – highlights pdf difference & store the result as image)
* 以视图模式比较PDF文件(较慢--对PDF文档进行像素逐一比较 -- 高亮PDF差异并将结果存储为图像)
```
String file1="c:/files/doc1.pdf";
String file1="c:/files/doc2.pdf";
 
// compares the pdf documents &amp; returns a boolean
// true if both files have same content. false otherwise.
// Default is CompareMode.TEXT_MODE
// 比较PDF文档并返回一个布尔值
// 两个PDF完全一样返回True， 不一样返回False
// 默认是 CompareMode.TEXT_MODE
pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
pdfUtil.compare(file1, file2);
 
// compare the 3rd page alone
pdfUtil.compare(file1, file2, 3, 3);
 
// compare the pages from 1 to 5
pdfUtil.compare(file1, file2, 1, 5);
 
//if you need to store the result
//需要的话，可以将不同的地方高亮并以图像存储到你的本地
pdfUtil.highlightPdfDifference(true);
pdfUtil.setImageDestinationPath("c:/imgpath");
pdfUtil.compare(file1, file2);
```


* For example, I have 2 PDF documents which have exact same content except the below differences in the charts.
![pdf1](http://i0.wp.com/www.testautomationguru.com/wp-content/uploads/2015/06/pdfu001.png) ![pdf2](http://i2.wp.com/www.testautomationguru.com/wp-content/uploads/2015/06/pdfu002.png)

The difference is shown as 
![diff](http://i1.wp.com/www.testautomationguru.com/wp-content/uploads/2015/06/pdfu003.png)
