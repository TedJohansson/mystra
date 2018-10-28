module com.mystra {
    requires javafx.fxml;
    requires javafx.controls;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires java.sql;
    requires java.activation;
    requires com.sun.xml.txw2;
    requires com.sun.istack.runtime;
    requires com.sun.xml.fastinfoset;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.materialicons;

    opens com.mystra;
    opens com.mystra.model;
    opens com.mystra.controller;
}