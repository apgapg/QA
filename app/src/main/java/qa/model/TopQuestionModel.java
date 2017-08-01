package qa.model;

/**
 * Created by master on 1/8/17.
 */

public class TopQuestionModel {
    private String id="";
    private String queid="";
    private String question="";
    private String image="";
    private String answers="";
    private String time="";
    private String upvotes="";
    private String hashtag="";
    private String uid="";
    private String status="";
    private String to_uid="";

    public String getImage() {
        return image;
    }

    public String getUid() {
        return uid;
    }

    public String getId() {
        return id;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public String getStatus() {
        return status;
    }

    public String getAnswers() {
        return answers;
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getQueid() {
        return queid;
    }

    public String getQuestion() {
        return question;
    }

    public String getTime() {
        return time;
    }

    public String getTo_uid() {
        return to_uid;
    }
}
