package ivan.takeout.common;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maximilian_Li
 */
public class SmsManager {
    public static String signName = "***";
    public static String templateCode = "***";

    public static List<String> validatePhoneNums = new ArrayList<>();

    static {
        validatePhoneNums.add("***");
        validatePhoneNums.add("***");
    }

    public static void sendMessage(String phoneNum, String param) throws Exception {
        Client client = createClient(
                "***",
                "***");

        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        boolean isIn = validatePhoneNums.contains(phoneNum);
        if (isIn) {
            sendSmsRequest
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setPhoneNumbers(phoneNum)
                    .setTemplateParam("{\"code\":\"" + param + "\"}");

        } else {
            sendSmsRequest
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setPhoneNumbers(validatePhoneNums.get(0))
                    .setTemplateParam("{\"code\":\"" + param + "\"}");
        }

        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
    }


    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config().setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }
}
