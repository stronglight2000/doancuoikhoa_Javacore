package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.Room;

import java.util.List;
import java.util.Scanner;

public interface IAction <T>{
    void insert(T obj);
    void update(Scanner scanner);
    void delete(Scanner scanner);
    void display(List<T> obj);
}
