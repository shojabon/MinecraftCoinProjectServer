package MinecraftCoinServer;

        import CommandExtentionServer.CommandExtentionServer;
        import SecureSocketChipV1.SSCV1;
        import SecureSocketChipV1.interfaces.SSCEvent;
        import SecureSocketChipV1.interfaces.SSCVCommand;

        import java.io.File;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.UUID;

public class MinecraftCoinServer extends SSCEvent implements SSCVCommand{

    CommandExtentionServer server;
    DatabaseConnector connector;
    ConfigLoader config = new ConfigLoader(new File("config.yml"));

    public MinecraftCoinServer(int port){
        connector = new DatabaseConnector(config.get("mysql.ip"), 3306, config.get("mysql.user"), config.get("mysql.password"), "minecraft-coin");
        server = new CommandExtentionServer(port, this, this);
    }

    public boolean ifTokenAvailable(String token){
        if(token.length() != 16){
            return false;
        }
        try {
            ResultSet res = connector.query("SELECT count(*) FROM server_account_list WHERE token ='" + connector.escapeStringForMySQL(token) + "';");
            int a = 0;
            while (res.next()) {
                a = res.getInt("count(*)");
            }
            res.close();
            return a >= 1;
        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public void onCommand(SSCV1 sscv1, String command, String[] args, UUID uuid) {
        if(sscv1.getCom().isCommunicationEncrypted() && !sscv1.isAuthenticated()){
            if(command.equalsIgnoreCase("AUTH")){
                if(args.length == 1){
                    if(ifTokenAvailable(args[0])){
                        sscv1.setAuthenticated(true);
                        System.out.println("authed");
                    }else{
                        sscv1.getCom().closeCommunication();
                    }
                }
            }
        }
    }


}
