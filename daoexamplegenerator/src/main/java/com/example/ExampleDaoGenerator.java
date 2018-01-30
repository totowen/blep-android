package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(4, "com.example.greenDao");
//      当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
//      Schema schema = n1920168.ew Schema(1, "me.itangqi.bean");
//      schema.setDefaultJavaPackageDao("me.itangqi.dao");

        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        // schema2.enableActiveEntitiesByDefault();
        // schema2.enableKeepSectionsByDefault();

        // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
        addNote(schema);

        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        new DaoGenerator().generateAll(schema, "/Users/alienware/AndroidStudioProjects/Launcher3/launcher3/src/main/java-gen");
    }
    /**
     * push test
     *
     * @param schema
     */
    private static void addNote(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity note1 = schema.addEntity("AppInstance");
        Entity note2 = schema.addEntity("AppRunRecord");
        Entity note3 = schema.addEntity("AppRunConfig");
        Entity note4 = schema.addEntity("AppPackage");
        Entity note5 = schema.addEntity("AppRunConfigDetail");
        Entity note6 = schema.addEntity("Time");
        // 你也可以重新给表命名
        // note.setTableName("NODE");
        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        //note.addIdProperty();
        //note.addStringProperty("text").notNull();
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.
        //note.addStringProperty("comment");
        //note.addDateProperty("date");

//表"AppInstance"
        note1.addDateProperty("installTime");
        note1.addStringProperty("mac");
        note1.addStringProperty("appPackageName").primaryKey();


//表"AppRunRecord"
        note2.addIdProperty();
        note2.addDateProperty("startTime");
        note2.addDateProperty("exitTime");
        note2.addStringProperty("appInstance");
        note2.addStringProperty("appInstanceId");
        note2.addStringProperty("runTime");
        note2.addIntProperty("allRunNumber");
        note2.addLongProperty("allRunDuration");
        note2.addStringProperty("appInstancePackageName");

//表AppRunConfig
        note3.addIdProperty();
        note3.addStringProperty("week");
        note3.addStringProperty("time");
        note3.addStringProperty("name");
        note3.addStringProperty("appIcon");
        note3.addStringProperty("packName");
        note3.addStringProperty("type");

//表"AppPackage"
        note4.addIdProperty();
        note4.addStringProperty("name");
        note4.addStringProperty("developer");
        note4.addStringProperty("description");
        note4.addDateProperty("createTime");
        note4.addStringProperty("star");
        note4.addStringProperty("typeDto");
        note4.addStringProperty("levelDto");
        note4.addStringProperty("appPackageName");

//表AppRunConfigDetail
        note5.addIdProperty();
        note5.addStringProperty("day");
        note5.addStringProperty("appStatus");
        note5.addStringProperty("padAppStatus");
        note5.addStringProperty("statusStartTime");
        note5.addStringProperty("statusEndTime");
        note5.addIntProperty("tag");
        note5.addStringProperty("state");//标记应用的状态

//表Time
        note6.addIdProperty();
        note6.addStringProperty("time");


        //表关联
        Property detailFKShelves = note5.addStringProperty("detailShelf_id").notNull().getProperty();
        note5.addToOne(note1, detailFKShelves);
        note1.addToMany(note5, detailFKShelves);

        Property timeFKShelves = note6.addLongProperty("timeShelf_id").notNull().getProperty();
        note6.addToOne(note5, timeFKShelves);
        note5.addToMany(note6, timeFKShelves);
    }


}

