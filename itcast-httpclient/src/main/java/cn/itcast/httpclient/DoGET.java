package cn.itcast.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DoGET {

    public static void main(String[] args) throws Exception {

        // 鍒涘缓Httpclient瀵硅薄
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 鍒涘缓http GET璇锋眰
        HttpGet httpGet = new HttpGet("http://manage.taotao.com/rest/content?categoryId=38&page=1&rows=6");

        CloseableHttpResponse response = null;
        try {
            // 鎵ц璇锋眰
            response = httpclient.execute(httpGet);
            // 鍒ゆ柇杩斿洖鐘舵�佹槸鍚︿负200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("内容："+content);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }

    }

}
