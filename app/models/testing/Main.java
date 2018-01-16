package models.testing;

public class Main {
    public static void main(String[] args) {
      /*  try {

            URL url = new URL("http://localhost:9000/test/persons");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }*/
      try {
          Encryptor encryptor = new Encryptor();
          String test = "TestString";
          System.out.println(test);
          encryptor.ecrypt(test);
          System.out.println(test);
          encryptor.decrypt(test);
          System.out.println(test);
      }catch (Exception ex){
          ex.printStackTrace();
      }
    }
}
