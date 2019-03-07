
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Win32Exception;
import java.io.File;
import java.io.IOException;
import waffle.windows.auth.IWindowsAuthProvider;
import waffle.windows.auth.IWindowsIdentity;
import waffle.windows.auth.IWindowsImpersonationContext;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        try {
            if (args.length == 3) {
                impersonation(args[0], args[1], args[2]);
            } else {
                System.out.println("Программа должна принимать три аргумента:\n1. Путь с именем файла\n2. Имя пользователя, от имени которого будет создаваться один из файлов\n3. Пароль пользователя, от имени которого будет создаваться один из файлов");
                System.exit(1);
            }
        } catch (Win32Exception var2) {
            System.out.println("Неверное имя пользователя или пароль");
        }

    }

    public static void impersonation(String path, String username, String password) {
        IWindowsAuthProvider prov = new WindowsAuthProviderImpl();
        IWindowsIdentity identity = prov.logonUser(username, password);
        createFile(pathParse(path));
        IWindowsImpersonationContext ctx = identity.impersonate();
        createFile(pathParse(path));
        ctx.revertToSelf();
        identity.dispose();
    }

    public static void createFile(String path) {
        File file = new File(path);

        try {
            if (file.createNewFile()) {
                System.out.println(path + " файл создан");
            } else {
                System.out.println("Файл " + path + " уже существует");
            }
        } catch (IOException var3) {
            System.out.println("У пользователя " + Advapi32Util.getUserName() + " нет прав на создания фалов по указанному пути");
        }

    }

    public static String pathParse(String default_str) {
        int start_name = default_str.lastIndexOf(92);
        String fileName = default_str.substring(start_name + 1);
        String path = default_str.substring(0, start_name + 1);
        String newName = Advapi32Util.getUserName() + "_" + fileName;
        return path + newName;
    }
}
