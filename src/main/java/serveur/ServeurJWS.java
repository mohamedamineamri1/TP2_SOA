package serveur;
import service.Calculatrice;
import javax.xml.ws.Endpoint;
public class ServeurJWS {
    public static void main(String[] args) {
        String url = "http://localhost:8080/calculatrice";
        Endpoint.publish(url,new Calculatrice());
        System.out.println("Service publié : " + url);
        System.out.println("WSDL           : " + url + "?wsdl");
    }
}
