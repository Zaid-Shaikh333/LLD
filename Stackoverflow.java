import java.util.*;

public class Stackoverflow {
    static HashMap<Question, Member> questionDB;
    public Stackoverflow(){

    }

    public static boolean addQuestionToDB(Question question, Member member)
    {
        questionDB.put(question, member);
        return true;
    }
}

class User{
    String name;
    String email;
    UserRole role;
}

enum UserRole{
    ADMIN, MEMBER, GUEST, SYSTEM;
}

enum UserStatus{
    ACTIVE, BLOCKED;
}

class Member extends User{
    String memberId;
    List<Question> postedQuestions;
    long reputation;
    UserStatus status;
    
    public Member(String name, String email, UserRole role)
    {
        this.name = name;
        this.email = email;
        this.role = role;
        this.postedQuestions = new ArrayList<Question>();
        this.status = UserStatus.ACTIVE;
    }

    public boolean postQuestion(String id,String title, String description, List<String> questionTags, Member member)
    {
        if(member.role != UserRole.MEMBER)
            return false;
        
        if(member.status != UserStatus.ACTIVE)
            return false;
        
        
        Question question = new Question(id, title, description, member, questionTags, new Date());
        Stackoverflow.addQuestionToDB(question, member);
        return true;
    }

    public boolean addComment(String id, String description, Member member, Question question)
    {
        Comment comment = new Comment(id, description, member, question);
        question.addResponseToQuestion(comment);
        return true;
    }

    public boolean upVoteQuestion(Question question, Member member)
    {
        question.upVote(member);
        return true;
    }

    public boolean downVoteQuestion(Question question, Member member)
    {
        question.downVote(member);
        return true;
    }

    public long getReputation()
    {
        return this.reputation;
    }
}

class Question{
    String questionId;
    String title;
    String description;
    long upvotes;
    Member member;
    List<String> questionTags;
    List<Comment> questionResponses;
    Date created;

    Set<Member> upVotedUsers;
    Set<Member> downVotedUsers;

    public Question(String id, String title, String description, Member member,List<String> tags, Date created)
    {
        this.questionId = id;
        this.title = title;
        this.description = description;
        this.member = member;
        this.questionTags.addAll(tags);
        this.created = created;
    }

    public void addResponseToQuestion(Comment comment)
    {
        questionResponses.add(comment);
    }

    public boolean upVote(Member member)
    {
        if(this.upVotedUsers.contains(member))
            return false;

        if(this.downVotedUsers.contains(member))
            downVotedUsers.remove(member);

        this.upVotedUsers.add(member);
        this.upvotes++;
        return true;
    }

    public boolean downVote(Member member)
    {
        if(this.downVotedUsers.contains(member))
            return false;
        
        if(this.upVotedUsers.contains(member))
            upVotedUsers.remove(member);

        this.downVotedUsers.add(member);
        this.upvotes--;
        return true;
    }
}

class Comment{
    String commentId;
    String description;
    Member member;
    long upvotes;
    Question question;

    public Comment(String id, String description, Member member, Question question)
    {
        this.commentId = id;
        this.description = description;
        this.member = member;
        this.question = question;
        this.upvotes = 0;
    }

}
