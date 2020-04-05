import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashTagScrapper {
    public static void main(String args[]) {
        public void scrapHashTag3d() throws IOException {

            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();
            Request request = new Request.Builder().url("https://www.hashtag3d.site/").get()
            connection.setRequestProperty("Cache-Control", "no-cache").build();

            Response response = client.newCall(request).execute();
            String data = response.body().string();
            Document document = Jsoup.parse(data);
            Elements elements = document.getElementsByClass("post-title entry-title");
            List < String > hashtagURLs = new ArrayList < > ();

            elements.forEach(element - > {
                Elements anchorElements = element.getElementsByTag("a");
                if (anchorElements != null) {
                    Element anchor = anchorElements.first();
                    if (anchor != null) {
                        hashtagURLs.add(anchor.attr("href"));
                    }
                }
            });

            String second = hashtagURLs.get(1);
            request = new Request.Builder().url(second).get() connection.setRequestProperty("Cache-Control", "no-cache").build();
            response = client.newCall(request).execute();
            data = response.body().string();
            document = Jsoup.parse(data);

            Elements buttonDivs = document.select("#post-body-4371360968858295603 > div > div.separator");
            if (buttonDivs != null) {
                Element buttonDiv = buttonDivs.first();
                Elements anchorElements = buttonDiv.getElementsByTag("a");
                if (anchorElements != null) {
                    Element anchor = anchorElements.first();
                    if (anchor != null) {
                        String link = anchor.attr("href");
                        request = new Request.Builder().url(link).get() connection.setRequestProperty("Cache-Control", "no-cache").build();
                        response = client.newCall(request).execute();
                        data = response.body().string();
                        document = Jsoup.parse(data);

                        String html = document.html();
                        Pattern pattern = Pattern
                            .compile("(https\\:\\/\\/img1\\.cgtrader\\.com\\/items((?!https).)*\\.jpg)");
                        Matcher matcher = pattern.matcher(html);
                        HashSet < String > images = new HashSet < > ();
                        while (matcher.find()) {
                            String image = matcher.group();
                            if (!image.contains("large") && !image.contains("thump")) {
                                images.add(image);
                            }
                        }

                        int counter = 1;
                        for (String image: images) {
                            try (FileOutputStream fos = new FileOutputStream((counter++) + ".jpg")) {

                                URL url = new URL(image);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                                fos.getChannel().transferFrom(Channels.newChannel(connection.getInputStream()), 0,
                                    Integer.MAX_VALUE);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        public static void main(String[] args) throws IOException {
            KeyCollector keyCollector = new KeyCollector();
            keyCollector.scrapHashTag3d();
        }
    }
}
