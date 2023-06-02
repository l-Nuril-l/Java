import javax.swing.*;
import java.net.CacheRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Hello world!");
        //1
//        int [] arr = new int[]{34,65,35,6,8,43,0,0,12,85,3,6};
//        System.out.println(Stream.of(34,65,35,6,8,43,0,0,12,85,3,6).filter(x -> x % 2 == 0).count());
//        System.out.println(Stream.of(34,65,35,6,8,43,0,0,12,85,3,6).filter(x -> x % 2 != 0).count());
//        System.out.println(Arrays.stream(arr).filter(x -> x == 0).count());
//        int num = s.nextInt();
//        System.out.println(Stream.of(34,65,35,6,8,43,0,0,12,85,3,6).filter(x -> x == num).count());

        //2
//        String[] Goroda = new String[]{"Киев" , "Харьков", "Одесса" , "Днепр" , "Донецк" , "Запорожье" , "Львов" , "Кривой Рог"};
//        Arrays.stream(Goroda).forEach(x -> System.out.print(x + " "));
//        System.out.println();
//        Arrays.stream(Goroda).filter(x -> x.length() > 6).forEach(x -> System.out.print(x + " "));
//        System.out.println();
//        String sity = s.nextLine();
//        System.out.println(Arrays.stream(Goroda).filter(x -> x.equals(sity)).count());
//        String startsWith = s.next();
//        Arrays.stream(Goroda).filter(x -> x.startsWith(startsWith)).forEach(x -> System.out.print(x + " "));

        //3
//        Automobile[] cars = new Automobile[]{
//                new Automobile("Lada","White",2000,199,400),
//                new Automobile("Hator","Yellow",2020,199,440),
//                new Automobile("Motik","Red",2012,199,400),
//                new Automobile("Raptor","Red",2011,456,600),
//                new Automobile("Beha","White",2000,999,700)};
//
//        Arrays.stream(cars).forEach(Automobile::print);
//        System.out.println();
//        Arrays.stream(cars).filter(x -> x.getColor().equals("White")).forEach(Automobile::print);
//        System.out.println();
//        Arrays.stream(cars).filter(x -> x.getEngineCapacity() == 400).forEach(Automobile::print);
//        System.out.println();
//        Arrays.stream(cars).filter(x -> x.getPrice() > 400).forEach(Automobile::print);
//        System.out.println();
//        Arrays.stream(cars).filter(x -> x.getYear() > 2010 && x.getYear() < 2015).forEach(Automobile::print);

        //4
        TV[] TVs = new TV[]{
                new TV("Lada",2000,3999,21,"Samsung"),
                new TV("Hator",2022,7999,27,"Samsung"),
                new TV("Motik",2012,2799,24,"LG"),
                new TV("Raptor",2011,2499,24,"Nokia"),
                new TV("Beha",2000,1999,21,"Asus")};

        Arrays.stream(TVs).forEach(TV::print);
        System.out.println();
        Arrays.stream(TVs).filter(x -> x.getDiagonal() == 24).forEach(TV::print);
        System.out.println();
        Arrays.stream(TVs).filter(x -> x.getManufacturer().equals("Samsung")).forEach(TV::print);
        System.out.println();
        Arrays.stream(TVs).filter(x -> x.getYear() == Calendar.getInstance().get(Calendar.YEAR)).forEach(TV::print);
        System.out.println();
        Arrays.stream(TVs).filter(x -> x.getPrice() > 2000).forEach(TV::print);
        System.out.println();
        Arrays.stream(TVs).sorted(Comparator.comparingInt(TV::getDiagonal)).forEach(TV::print);
        System.out.println();
        Arrays.stream(TVs).sorted(Comparator.comparingInt(TV::getDiagonal).reversed()).forEach(TV::print);
        System.out.println();
    }


}
