package service;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
@WebService(
        endpointInterface = "service.ICalculatrice",
        serviceName = "CalculatriceWS",
        portName = "CalculatricePort",
        targetNamespace = "http://service/")
public class Calculatrice implements ICalculatrice {
    @Override
    @WebMethod(operationName= "somme")
    public double somme(@WebParam(name = "a") double a,
                        @WebParam(name = "b") double b) {
        return a + b;
    }
    @Override
    @WebMethod(operationName= "soustraction")
    public double soustraction(@WebParam(name= "a") double a,
                               @WebParam(name= "b") double b) {
        return a - b;
    }
    @Override
    @WebMethod(operationName= "multiplication")
    public double multiplication(@WebParam(name= "a") double a,
                                 @WebParam(name= "b") double b) {
        return a * b;
    }
    @Override
    @WebMethod(operationName= "division")
    public double division(@WebParam(name= "a") double a,
                           @WebParam(name= "b") double b) {
        if (b==0) {
            // Sera transformée en SOAP Fault côté client
            throw new IllegalArgumentException("Division par zéro impossible");
        }
        return a / b;
    }
}
