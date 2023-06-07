package com.darkerbox.hacktools.Encrypt;

public class BigIPDecode {
    public static String deocde(String encoded_string){
        // https://cn-sec.com/archives/265560.html
//        encoded_string = "1030031882.22811.0000";
        String[] encode_array = encoded_string.split("\\.");
        if (encode_array.length==3){
            String encode_host = encode_array[0];
            String encode_port = encode_array[1];
            // 解码host
            String hosttmp = Integer.toHexString(Integer.parseInt(encode_host));
            if (hosttmp.length()!=8){
                hosttmp = "0"+hosttmp;
            }

            String result_tmp = "";
            for (int i = 0; i < hosttmp.length(); i=i+2) {
                result_tmp = String.valueOf(Integer.parseInt(hosttmp.substring(i,i+2), 16)) +"."+ result_tmp;
            }
            String host = result_tmp.substring(0,result_tmp.length()-1)+":";
            // 解码端口
            String porttmp = Integer.toHexString(Integer.parseInt(encode_port));
            String tmp = "";
            for (int i = 0; i < porttmp.length(); i=i+2) {
                tmp = porttmp.substring(i,i+2) + tmp;
            }

            int port = Integer.parseInt(tmp,16);
            return host+port;
        }
        return "输入格式错误 Example: 1030031882.22811.0000";
    }


}
