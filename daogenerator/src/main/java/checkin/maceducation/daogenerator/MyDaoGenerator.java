package checkin.maceducation.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(12, "checkin.maceducation.daogenerator");
        Entity CheckIn = schema.addEntity("CheckIn");

        CheckIn.addIdProperty().autoincrement().primaryKey();
        CheckIn.addIntProperty("TripEaID");
        CheckIn.addStringProperty("CheckInName");
        CheckIn.addStringProperty("CheckInAddress");
        CheckIn.addStringProperty("CheckInDate");
        CheckIn.addStringProperty("Latitude");
        CheckIn.addStringProperty("Longitude");
        CheckIn.addStringProperty("Remark");
        CheckIn.addStringProperty("Image");
        CheckIn.addIntProperty("CheckInType");
        CheckIn.addIntProperty("StatusSend");
        CheckIn.addIntProperty("TripEaCustomerID");
        CheckIn.addIntProperty("EnergyTypeID");
        CheckIn.addDoubleProperty("EnergyPrice");
        CheckIn.addIntProperty("ActivityID");
        CheckIn.addStringProperty("CheckInActivityRemark");
        CheckIn.addStringProperty("CheckInActivityContact");
        CheckIn.addStringProperty("CheckInActivityPosition");
        CheckIn.addStringProperty("CheckInActivityEmail");
        CheckIn.addStringProperty("CheckInActivityTel");
        CheckIn.addIntProperty("SubjectID");
        CheckIn.addIntProperty("EAID");
        //     new DaoGenerator().generateAll(schema, args[0]);

//        Entity CustomerActivity = schema.addEntity("Activity");
//        CustomerActivity.addIdProperty().autoincrement().primaryKey();
//        CustomerActivity.addIntProperty("CustomerCheckInID");
//        CustomerActivity.addIntProperty("ActivityID");
//        CustomerActivity.addStringProperty("ActivityName");
//        CustomerActivity.addIntProperty("StatusSend");
//        CustomerActivity.addIntProperty("EAID");
//        CustomerActivity.addIntProperty("SubjectID");
//        CustomerActivity.addStringProperty("ContractName");
//        CustomerActivity.addStringProperty("ContractTel");
//        CustomerActivity.addStringProperty("ActivityRemark");

        new DaoGenerator().generateAll(schema, args[0]);
    }
}