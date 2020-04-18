package com.example.mad.articlenews;

class ArticleDetails {
    private String title,article,author;
    String id,imagePost;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArticleDetails() {
    }

    public ArticleDetails(String title, String article, String author, String id,String imagePost) {
        this.title = title;
        this.article = article;
        this.author = author;
        this.id = id;
        this.imagePost  = imagePost;
    }

    public String getImagePost() {
        return imagePost;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
