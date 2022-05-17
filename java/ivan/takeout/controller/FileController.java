package ivan.takeout.controller;

import ivan.takeout.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件的上传与下载
 *
 * @author Maximilian_Li
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class FileController {

    @Value("${up-and-down-file.path}")
    private String basePath;

    /**
     * 文件由浏览器端上传到服务器端
     */
    @PostMapping("/upload")
    public R<String> fileUpload(MultipartFile file) {

        // 1. 组合新文件名
        String oName = file.getOriginalFilename();
        assert oName != null;
        String prefix = oName.subSequence(0, oName.lastIndexOf(".")) + "@" + UUID.randomUUID();
        String suffix = oName.substring(oName.lastIndexOf("."));
        String fName = prefix + suffix;

        // 2. 判断根目录的存在性
        File directory = new File(basePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            // 3. file是一个临时文件，如果未经转存的话，就会自动消失
            file.transferTo(new File(basePath + fName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fName);
    }

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void fileDownload(String name, HttpServletResponse response) {
        // 1. 通过输入流读文件内容
        try (FileInputStream fis = new FileInputStream(basePath + name);
             ServletOutputStream os = response.getOutputStream()) {

            // 设置返回文件类型
            response.setContentType("image/jpeg");

            // 2. 通过输出流写回浏览器
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = fis.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                os.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
