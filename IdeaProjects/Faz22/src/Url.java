import java.io.*;
import java.net.URL;
import java.util.Scanner;

/**
 * We use this class to organize the meaning of each input
 * that include at least 2 word.
 */

public class Url {
    /**
     * @param list = to know we want to show the list of our request or not
     * @param nameFile = to save the file name that we want to save the request
     */
    private boolean list = false;
    private String nameFile[];

    /**
     * This method is for get input and split it and the program end when send EXIT
     * and show all the request that we saved (jurl> list)
     * fire can show the respond of selected item in list in order (jurl> fire 2 3)
     * help can show some information about program
     * input after -M get the method
     * input after -h get the header and value
     * input after -d get the form data
     * -s save the request
     * -o save the name (its our choice to git the name or not)
     * -i chose to shoe the header of respond of not
     * -f follow redirect for some URL s
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void Request(String str) throws IOException, ClassNotFoundException {;

        if (str == "EXIT"){
            return;
        }
        String[] arrOfStr = str.split(" ");

        for (String i: arrOfStr){
            System.out.println(i);
        }

        System.out.println("number:     " +arrOfStr.length);
        if (arrOfStr.length == 1) {
            System.out.println("You cant have this input!!!!");
            System.out.println("*****************" +arrOfStr[0]);
            return;
//                continue;
        }

        if (arrOfStr[1].equals("list")) {
            List();
        }
        else if (arrOfStr[1].equals("fire")) {

            if (list) {
                int number[] = new int[nameFile.length];
                int check = 0;
                for (int i = 2; i < arrOfStr.length; i++) {
                    try {
                        if (Integer.parseInt(arrOfStr[i]) <= nameFile.length && Integer.parseInt(arrOfStr[i]) != 0) {
                            number[i - 2] = Integer.parseInt(arrOfStr[i]);
                            check++;
                        } else {
                            System.out.println("You cant choose this number(s). try again!");
                            break;
                        }
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("you cant have this input. try again!");
                        break;
                    }
                }
                if (check == arrOfStr.length - 2)
                    Fire(number);
            }
            else
                System.out.println("You have to print a list first(>jurl list)!");
        }
        else if (arrOfStr[1].equals("--help") || arrOfStr[1].equals("-h")) {
            System.out.println(
                    " -d, --data <data>   HTTP POST data\n" +
                            "     --data-ascii <data> HTTP POST ASCII data\n" +
                            "     --data-binary <data> HTTP POST binary data\n" +
                            "     --data-raw <data> HTTP POST data, '@' allowed\n" +
                            "     --data-urlencode <data> HTTP POST data url encoded\n" +
                            "     --delegation <LEVEL> GSS-API delegation permission\n" +
                            "     --digest Use HTTP Digest Authentication\n" +
                            " -H, --header <header/@file> Pass custom header(s) to server\n" +
                            " -h, --help This help text\n" +
                            " -i  show whether the headers displayed or not.\n" +
                            " -List  show all the requests\n" +
                            " -O  --output Can save the respond in .txt file\n" +
                            " -S  --save Can save the request in .txt file\n");
            list = false;
//                continue;
        } else {
            Save save = new Save();
            URL url = null;
            String Body[] = null;
            String Header[] = null;
            boolean saveRespond = false;
            boolean saveRequest = false;
            boolean headerRespond = false;
            boolean followRedirect = false;
            String nameOutput = null;
            try {
                url = new URL(arrOfStr[1]);
            } catch (Exception e) {
                System.out.println("You cant have this url!");
//                    continue;
            }
            String method = "GET";


            for (int i = 0; i < arrOfStr.length; i++) {

                if (arrOfStr[i].equals("-d") || arrOfStr[i].equals("-date")) {
                    Body = arrOfStr[i + 1].split("&");
                } else if (arrOfStr[i].equals("-H") || arrOfStr[i].equals("--header")) {
                    Header = arrOfStr[i + 1].split(";");
                } else if (arrOfStr[i].equals("-M") || arrOfStr[i].equals("--method")) {
                    if (arrOfStr[i + 1].equals("POST") || arrOfStr[i + 1].equals("GET") || arrOfStr[i + 1].equals("HEAD") ||
                            arrOfStr[i + 1].equals("PUT") || arrOfStr[i + 1].equals("DELETE") || arrOfStr[i + 1].equals("CONNECT") ||
                            arrOfStr[i + 1].equals("OPTIONS") || arrOfStr[i + 1].equals("PATCH") || arrOfStr[i + 1].equals("TRACE"))
                        method = arrOfStr[i + 1];
                    else {
                        System.out.println("You cant have this method so the method is GET default");
                    }
                } else if (arrOfStr[i].equals("-S") || arrOfStr[i].equals("--save")) {
                    saveRequest = true;
                } else if (arrOfStr[i].equals("-O") || arrOfStr[i].equals("--outPut")) {
                    saveRespond = true;
                    if (i + 1 < arrOfStr.length) {
                        if (!arrOfStr[i + 1].contains("-"))
                            nameOutput = arrOfStr[i + 1];
                        else
                            nameOutput = "";
                    } else {
                        nameOutput = "";
                    }
                } else if (arrOfStr[i].equals("-i")) {
                    headerRespond = true;
                } else if (arrOfStr[i].equals("-f")){
                    followRedirect = true;
                }
            }

            Request request = new Request(url, method, Body, Header);
            if (saveRequest) {
                save.SaveRequest(request);
            }
            request.createRequest(saveRespond, nameOutput, headerRespond, followRedirect);
            list = false;
        }
//        }

    }

    /**
     * This method can show us information of each request that we saved
     */
    public void List(){

        int counterFile = 0;
        int counter = 1;

        list = true;
        File folder = new File("/Users/sara/IdeaProjects/Faz2.2");
        File[] listOfFiles = folder.listFiles();

        int Counter= 0 ;
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(".txt") && file.getName().contains("request")) {
                Counter++;
            }
        }
        nameFile = new String[Counter];
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(".txt") && file.getName().contains("request")) {
                try {
                    nameFile[counterFile++] = file.getName();
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    Request request = (Request) in.readObject();

                    System.out.print(counter++ + ") url: " + request.getUrl() + " | method: " + request.getMethod());
                    if (request.getHeader() != null) {
                        System.out.print(" | header: ");
                        for (String b : request.getHeader())
                            System.out.print(b + " ");
                    }
                    if (request.getBody() != null) {
                        System.out.print(" | body: ");
                        for (String h : request.getBody())
                            System.out.print(h + " ");
                    }
                    System.out.println("|");
                    in.close();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("**************");
    }

    /**
     * This method can show us the respond of each request that we chose in the list
     * @param number numbers of request that we want to see the respond
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void Fire(int number[]) throws IOException, ClassNotFoundException {

        if (list) {
            int counter = 1;
            for (int n = 0; n < number.length; n++) {
                for (int i = 0; i <= nameFile.length; i++) {
                    if (number[n] == i && number[n] != 0) {
                        System.out.println(nameFile[i - 1]);

                        File file = new File(nameFile[i - 1]);
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                        Request request = (Request) in.readObject();

                        System.out.print(counter++ + ") url: " + request.getMethod() + " | method: " + request.getMethod());
                        if (request.getHeader() != null) {
                            System.out.print(" | header: ");
                            for (String b : request.getHeader())
                                System.out.print(b + " ");
                        }
                        if (request.getBody() != null) {
                            System.out.print(" | body: ");
                            for (String h : request.getBody())
                                System.out.print(h + " ");
                        }
                        System.out.println("|");
                        System.out.println(request.getMethod());
                        request.createRequest(false, null, false, false);

                        in.close();
                    }
                }
            }

            list = false;
        }
    }



}
