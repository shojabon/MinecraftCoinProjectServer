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

    @Override
    public void onCommand(SSCV1 sscv1, String command, String[] args, UUID uuid) {
        if(command.equalsIgnoreCase("testa")){
            try {
                ResultSet res = connector.query("SELECT * FROM server_account_list");
                while (res.next()) {
                    sscv1.getCom().sendMessage(res.getString("name"));
                }
                res.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


}
