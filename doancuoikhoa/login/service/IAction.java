package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.Room;
import doancuoikhoa.login.entities.User;

import java.util.List;
import java.util.Scanner;

public interface IAction <T>{
    void insert(T obj);
    void delete(Scanner scanner, User user);

    void update(Scanner scanner, User user);

    void display(List<T> obj);
}
