import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws InterruptedException {
        sb.append("Информация об установке:\n");
        //Создание файлов и каталогов
        makeDir("D:\\Java Project\\Files\\Games\\src");
        makeDir("D:\\Java Project\\Files\\Games\\res");
        makeDir("D:\\Java Project\\Files\\Games\\savegames");
        makeDir("D:\\Java Project\\Files\\Games\\temp");
        makeDir("D:\\Java Project\\Files\\Games\\src\\main");
        makeDir("D:\\Java Project\\Files\\Games\\src\\test");
        makeDir("D:\\Java Project\\Files\\Games\\res\\drawables");
        makeDir("D:\\Java Project\\Files\\Games\\res\\vectors");
        makeDir("D:\\Java Project\\Files\\Games\\res\\icons");
        makeFile("D:\\Java Project\\Files\\Games\\src\\main\\Main.java");
        makeFile("D:\\Java Project\\Files\\Games\\src\\main\\Utils.java");

        sb.append("\n");

        GameProgress s1 = new GameProgress(95, 2, 3, 12.3);
        GameProgress s2 = new GameProgress(23, 4, 5, 45.9);
        GameProgress s3 = new GameProgress(89, 12, 55, 95.8);

        sb.append("\n");

        //Сериализация
        saveGames("D:\\Java Project\\Files\\Games\\savegames\\save1.dat", s1);
        saveGames("D:\\Java Project\\Files\\Games\\savegames\\save2.dat", s2);
        saveGames("D:\\Java Project\\Files\\Games\\savegames\\save3.dat", s3);

        sb.append("\n");


        //Создание архивов
        makeZip("D:\\Java Project\\Files\\Games\\savegames\\zipSave1.zip", "D:\\Java Project\\Files\\Games\\savegames\\save1.dat", "zipsave1.dat");
        makeZip("D:\\Java Project\\Files\\Games\\savegames\\zipSave2.zip", "D:\\Java Project\\Files\\Games\\savegames\\save2.dat", "zipsave2.dat");
        makeZip("D:\\Java Project\\Files\\Games\\savegames\\zipSave3.zip", "D:\\Java Project\\Files\\Games\\savegames\\save3.dat", "zipsave3.dat");

        sb.append("\n");

        //Удаление файлов
        delFile("D:\\Java Project\\Files\\Games\\savegames\\save1.dat");
        delFile("D:\\Java Project\\Files\\Games\\savegames\\save2.dat");
        delFile("D:\\Java Project\\Files\\Games\\savegames\\save3.dat");

        sb.append("\n");

        //Распаковка архивов
        openZip("D:\\Java Project\\Files\\Games\\savegames\\zipSave1.zip", "D:\\Java Project\\Files\\Games\\savegames");

        sb.append("\n");

        //Десериализация
        openProgress("D:\\Java Project\\Files\\Games\\savegames\\zipsave1.dat");

        infoFile("D:\\Java Project\\Files\\Games\\temp\\temp.txt", sb);
    }

    public static void makeDir(String dirPath) {
        Date date = new Date();
        File dir = new File(dirPath);
        if (dir.mkdir()) {
            int lastIndex = dirPath.lastIndexOf("\\");
            String name = dirPath.substring(lastIndex + 1);
            System.out.println("Каталог " + name + " создан - " + date);
            sb.append("Каталог " + name + " создан - " + date + "\n");
        }
    }

    public static void makeFile(String name) {
        Date date = new Date();
        File file = new File(name);
        try {
            if (file.createNewFile()) {
                int lastIndex = name.lastIndexOf("\\");
                String n = name.substring(lastIndex + 1);
                System.out.println("Файл " + n + " создан - " + date);
                sb.append("Файл " + n + " создан - " + date + "\n");
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void infoFile(String name, StringBuilder sb) {
        try (FileWriter file = new FileWriter(name, false)) {
            file.write(sb.toString());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void saveGames(String path, GameProgress s) {
        Date date = new Date();
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(s);
            int lastIndex = path.lastIndexOf("\\");
            String n = path.substring(lastIndex + 1);
            System.out.println("Файл " + n + " создан - " + date);
            sb.append("Файл " + n + " создан - " + date + "\n");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void makeZip(String path, String archivedFile, String nameInZip) {
        Date date = new Date();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path));
             FileInputStream fis = new FileInputStream(archivedFile)) {
            ZipEntry entry = new ZipEntry(nameInZip);
            zos.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zos.write(buffer);
            zos.closeEntry();
            int lastIndex = path.lastIndexOf("\\");
            String n = path.substring(lastIndex + 1);
            System.out.println("Архив " + n + " создан - " + date);
            sb.append("Архив " + n + " создан - " + date + "\n");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void delFile(String path) {
        Date date = new Date();
        File file = new File(path);
        try {
            if (file.delete()) {
                int lastIndex = path.lastIndexOf("\\");
                String n = path.substring(lastIndex + 1);
                System.out.println("Файл " + n + " удален - " + date);
                sb.append("Файл " + n + " удален - " + date + "\n");
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void openZip(String path, String openFilePath) {
        Date date = new Date();
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path))) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fos = new FileOutputStream(openFilePath + "\\" + name);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
            int lastIndex = path.lastIndexOf("\\");
            String n = path.substring(lastIndex + 1);
            System.out.println("Архив " + n + " распакован - " + date);
            sb.append("Архив " + n + " распакован - " + date + "\n");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void openProgress(String path) {
        Date date = new Date();
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        int lastIndex = path.lastIndexOf("\\");
        String n = path.substring(lastIndex + 1);
        System.out.println("Файл " + n + " открыт - " + date + "\n Содержание файла: \n" + gameProgress);
        sb.append("Файл " + n + " открыт - " + date + "\n Содержание файла: \n" + gameProgress);
    }
}
