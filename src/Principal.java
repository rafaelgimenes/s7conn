


import java.io.IOException;

import com.github.s7connector.api.DaveArea;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.factory.S7ConnectorFactory;


/**
 * @author Rafael Gimenes Leite <rafael.leite@vesuvius.com>
 * 2 de out de 2019
 */
public class Principal {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String VERSAO = "0.1";
		
		//simulation
		
		//IP<String> Rack<INT> SLOT<INT> DB<INT> OFFSET<INT> HEXA<STRING>

		//10.1.1.2 0 0 100 2 03EF0315
		
		//conexao PLC
		S7Connector connector=null;
		//dados do PLC
		int rack = 0;
		int slot = 0;
		int offset = 0;
		int DB = 0;
		String ip = "";
		String strByteArrays="";
		byte[] bufferToWrite = new byte[10];
		//versao
		System.out.println("S7conn: " + VERSAO);
		
		
		try {
			if(args!=null){

				ip = args[0];
				rack = Integer.parseInt(args [1]);
				slot = Integer.parseInt(args [2]);
				DB = Integer.parseInt(args [3]);
				offset = Integer.parseInt(args [4]);
				strByteArrays = args[5];

				bufferToWrite = Util.toByteArray(strByteArrays);
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			try {
				Util.gravaLinha("S7log arguments get error: "+args.toString(), "S7Log.txt", "true");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		System.exit(0);
		}

        //argumentos insuficientes mata o sw.
        if(args!=null){
        	if(args.length<=5){
        		try {
    				Util.gravaLinha("S7log Insuficients arguments: "+args.toString(), "S7Log.txt", "true");
    			} catch (IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
        		System.exit(0);
        		
        	}
        }

        try {
        //Open TCP Connection
        connector = 
        		S7ConnectorFactory
        		.buildTCPConnector()
        		.withHost(ip)
        		.withRack(rack)
        		.withSlot(slot)
        		.build();
        }catch (Exception e) {
			e.printStackTrace();
			try {
				Util.gravaLinha("S7log OpenConnection Error: "+e.toString(), "S7Log.txt", "true");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

        
        try {
        	connector.write(DaveArea.DB, DB, offset, bufferToWrite);	
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Util.gravaLinha("S7log Write Error: "+e.toString(), "S7Log.txt", "true");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        

        //Close connection
        try {
        	connector.close();
        } catch (IOException e) {
        	e.printStackTrace();
        	try {
				Util.gravaLinha("S7log CloseConnection Error: "+e.toString(), "S7Log.txt", "true");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}

}
