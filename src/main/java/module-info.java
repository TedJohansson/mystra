module com.mystra {
    requires javafx.fxml;
    requires javafx.controls;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires java.sql;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires java.activation;
    requires com.sun.xml.txw2;
    requires com.sun.istack.runtime;
    requires com.sun.xml.fastinfoset;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.materialicons;

    opens com.mystra;
    opens com.mystra.model;
    opens com.mystra.controller;
}