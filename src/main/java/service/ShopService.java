package service;

import controller.ViewController;
import database.DBHandler;

import java.sql.Connection;

public class ShopService extends ViewController {
    private Connection connection = DBHandler.getConnection();



}
