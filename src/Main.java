import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

  public static void main(String[] args) {
    GameProgress gameProgress1 = new GameProgress(5, 5, 2, 120.5);
    GameProgress gameProgress2 = new GameProgress(4, 5, 4, 133);
    GameProgress gameProgress3 = new GameProgress(3, 4, 8, 455);
    saveGame("C:\\Games\\savegames\\save1.dat", gameProgress1);
    saveGame("C:\\Games\\savegames\\save2.dat", gameProgress2);
    saveGame("C:\\Games\\savegames\\save3.dat", gameProgress3);
    String fileList = "C:\\Games\\savegames\\save1.dat\nC:\\Games\\savegames\\save2.dat\nC:\\Games\\savegames\\save3.dat";
    zipFiles("C:\\Games\\savegames\\save.zip", fileList);
    deleteFiles(fileList);
  }

  public static void saveGame(String filePath, GameProgress gameProgress) {
    try (FileOutputStream file = new FileOutputStream(filePath);
         ObjectOutputStream obj = new ObjectOutputStream(file)) {
      obj.writeObject(gameProgress);
      System.out.println("Создан файл " + filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void zipFiles(String zipPath, String fileList) {
    try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipPath))) {
      String[] list = fileList.split("\n");
      for (String filePath : list) {
        try (FileInputStream file = new FileInputStream(filePath)) {
          ZipEntry entry = new ZipEntry(filePath.substring(filePath.lastIndexOf("\\") + 1));
          zip.putNextEntry(entry);
          byte[] buffer = new byte[file.available()];
          file.read(buffer);
          zip.write(buffer);
          zip.closeEntry();
          System.out.println("Файл " + filePath + " помещен в архив");
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void deleteFiles(String fileList) {
    String[] list = fileList.split("\n");
    for (String filePath : list) {
      File file = new File(filePath);
      if (file.delete())
        System.out.println("Удален файл " + filePath);
    }
  }
}
