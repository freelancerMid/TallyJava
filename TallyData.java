import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class TallyData {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String title = "Day book";
        String from_dt = "1-4-2022";
        String to_dt = "30-4-2022";

        try {
            String response = get_data(get_payload(title, from_dt, to_dt));
            String xmlData = stringToXml(response);
            System.out.println(response);
        } catch (ConnectException e) {
            System.out.println("Connection error...");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException: Unknown error.....");
        } catch (IOException e) {
            System.out.println("IOException: Input Output error.....");
        }
    }

    // GENERATING PAYLOAD
    // HERE...............................................................

    private static String get_payload(String r_type, String from_dt, String to_dt) {
        System.out.println("Payload is Creating....");
        String xml = "";

        xml = "<ENVELOPE><HEADER><VERSION>1</VERSION><TALLYREQUEST>EXPORT</TALLYREQUEST>";
        xml += "<TYPE>DATA</TYPE><ID>" + r_type + "</ID></HEADER><BODY>";
        xml += "<DESC><STATICVARIABLES><SVEXPORTFORMAT>$$SysName:XML</SVEXPORTFORMAT>";
        xml += "<SVFROMDATE Type='DATE'>" + from_dt + "</SVFROMDATE><SVTODATE Type='DATE'>" + to_dt;
        xml += "</SVTODATE></STATICVARIABLES></DESC></BODY></ENVELOPE>";

        System.out.println("Payload is Created");

        return xml;
    }

    // CONNECTING FROM THE
    // TALLY................................................................
    private static String get_data(String payload) throws UnsupportedEncodingException, IOException {

        URL url = new URL("http://localhost:9002");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        try {
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(payload.getBytes("UTF-8"));

            String result = "";
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((line = reader.readLine()) != null) {
                result += line;
            }

            return result;

        } catch (ProtocolException e) {
            System.out.println("ProtocolException: Protocol type error.....");
        }
        return null;

    }

    private static String stringToXml(String data) {


        return null;

    }
}