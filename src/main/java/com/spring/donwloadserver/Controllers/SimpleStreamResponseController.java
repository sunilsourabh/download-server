package com.spring.donwloadserver.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping(path = "/download")
public class SimpleStreamResponseController {


    @GetMapping(path = "/raw")
    public StreamingResponseBody srtreamFile(){
        return out -> {
            for (int i = 0; i < 1000; i++) {
                out.write((Integer.toString(i) + " - ")
                        .getBytes());
                out.flush();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    @GetMapping(path = "/pdf")
    public StreamingResponseBody streamCsvFile(HttpServletResponse response) throws FileNotFoundException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"sample2.pdf\"");
        InputStream inputStream = new FileInputStream(new File("C:\\sample2.pdf"));

        return out -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                System.out.println("Writing some bytes..");
                out.write(data, 0, nRead);
                out.flush();
            }
        };
    }
}
