package com.testautomationguru.utility;

import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

public class PDFUtilTest {

    PDFUtil pdfutil = new PDFUtil();

    /**
     * 测试方法前置条件
     */
    @BeforeClass
    public void beforeApprovalConditions(){
        //启动日志打印 默认为 info
        pdfutil.enableLog();
        //使用图片模式进行比对
        pdfutil.setCompareMode(CompareMode.VISUAL_MODE);
        //比对所有页数
        pdfutil.setBCompareAllPages(true);
    }
    /**
     * 获取pdf文件的页数
     * @throws IOException
     */
    @Test(priority = 1)
    public void checkForPDFPageCount() throws IOException {
        int actual = pdfutil.getPageCount(getFilePath("image-extract/sample.pdf"));
        Assert.assertEquals(actual, 6);
    }

    /**
     * 获取pdf中的文字
     * @throws IOException
     */
    @Test(priority = 2)
    public void checkForFileContent() throws IOException {
//        String actual1 = pdfutil.getText(getFilePath("image-extract/sample.pdf"));
//        System.out.println(actual1);
        String actual = pdfutil.getText(getFilePath("text-extract/sample.pdf"));
        String expected = Files.readFile(new File(getFilePath("text-extract/expected.txt")));
        Assert.assertEquals(actual.trim(), expected.trim());
    }

    /**
     * 使用PDFTextStriper修改文本提取策略，过滤部分文字进行对比
     * @throws IOException
     */
    @Test(priority = 3)
    public void checkForFileContentUsingStripper() throws IOException {
        String actual = pdfutil.getText(getFilePath("text-extract-position/sample.pdf"));
        String expected = Files.readFile(new File(getFilePath("text-extract-position/expected.txt")));
        Assert.assertNotEquals(actual.trim(), expected.trim());

        //should match with stripper
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setSortByPosition(true);
        pdfutil.useStripper(stripper);
        actual = pdfutil.getText(getFilePath("text-extract-position/sample.pdf"));
        expected = Files.readFile(new File(getFilePath("text-extract-position/expected.txt")));
        Assert.assertEquals(actual.trim(), expected.trim());
        pdfutil.useStripper(null);
    }

    /**
     * 从PDF中提取图片
     * @throws IOException
     */
    @Test(priority = 4)
    public void extractImages() throws IOException {
        List<String> actualExtractedImages = pdfutil.extractImages(getFilePath("image-extract/sample.pdf"));
        String imageDestinationPath = pdfutil.getImageDestinationPath();
        System.out.println("存储图片路径： "+imageDestinationPath);
        Assert.assertEquals(actualExtractedImages.size(), 7);
    }

    /**
     * 保存图片
     * @throws IOException
     */
    @Test(priority = 5)
    public void saveAsImages() throws IOException {
        List<String> actualExtractedImages = pdfutil.savePdfAsImage(getFilePath("image-extract/sample.pdf"));
        String imageDestinationPath = pdfutil.getImageDestinationPath();
        System.out.println("存储图片路径： "+imageDestinationPath);
        Assert.assertEquals(actualExtractedImages.size(), 6);
    }

    /**
     * 对比PDF文本模式差异
     * @throws IOException
     */
    @Test(priority = 6)
    public void comparePDFTextModeDiff() throws IOException {
        String file1 = getFilePath("text-compare/sample1.pdf");
        String file2 = getFilePath("text-compare/sample2.pdf");
        pdfutil.setCompareMode(CompareMode.TEXT_MODE);

        boolean result = pdfutil.compare(file1, file2);
        Assert.assertFalse(result);
    }

    /**
     * 设置文本过滤器，对比PDF文本模式差异
     * @throws IOException
     */
    @Test(priority = 7)
    public void comparePDFTextModeSameAfterExcludePattern() throws IOException {
        String file1 = getFilePath("text-compare/sample1.pdf");
        String file2 = getFilePath("text-compare/sample2.pdf");
        pdfutil.setCompareMode(CompareMode.TEXT_MODE);
        pdfutil.excludeText("\\d+");
        // pdfutil.excludeText("1999","1998");
        boolean result = pdfutil.compare(file1, file2);
        Assert.assertTrue(result);
    }

    /**
     * 以图片模式比较PDF相同之处
     * 注意：pdf页数需要相同
     * @throws IOException
     */
    @Test(priority = 8)
    public void comparePDFImageModeSame() throws IOException {
        String file1 = getFilePath("image-compare-same/sample1.pdf");
        String file2 = getFilePath("image-compare-same/sample2.pdf");
        pdfutil.setCompareMode(CompareMode.VISUAL_MODE);

        boolean result = pdfutil.compare(file1, file2);
        Assert.assertTrue(result);
    }
    /**
     * 以图片模式比较PDF不同之处（高亮展示）
     * 注意：pdf页数需要相同
     * @throws IOException
     */
    @Test(priority = 9)
    public void comparePDFImageModeDiff() throws IOException {
        //高亮展示
        pdfutil.highlightPdfDifference(true);
        String file1 = getFilePath("image-compare-diff/厂区封闭化改造项目合同.pdf");
        String file2 = getFilePath("image-compare-diff/厂区封闭化改造项目合同对比.pdf");
        boolean result = pdfutil.compare(file1, file2);
        Assert.assertFalse(result);
    }

    /**
     * 从指定页开始比对
     * @throws IOException
     */
    @Test(priority = 10)
    public void comparePDFImageModeDiffSpecificPage() throws IOException {
        pdfutil.highlightPdfDifference(true);
        String file1 = getFilePath("image-compare-diff/sample1.pdf");
        String file2 = getFilePath("image-compare-diff/sample2.pdf");
        boolean result = pdfutil.compare(file1, file2, 3);
        Assert.assertTrue(result);
    }

    /**
     * 获取文件所在绝对路径
     * @param filename
     * @return
     */
    private String getFilePath(String filename) throws UnsupportedEncodingException {
        URL resource = getClass().getClassLoader().getResource(filename);
        String file = resource.getFile();
        file = java.net.URLDecoder.decode(file,"utf-8");
        String absolutePath = new File(file).getAbsolutePath();
        return absolutePath;
    }
}
