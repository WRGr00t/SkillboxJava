public class Link implements Comparable<Link> {
    String url;
    int level;

    public Link(String url, int level) {
        this.url = url;
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(Link link) {
        return this.url.compareTo(link.getUrl());
    }
}
