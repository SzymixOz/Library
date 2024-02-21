package pl.edu.agh.model.loans;

import jakarta.persistence.*;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.users.Member;

import java.util.Date;

@Entity
public class HistoricalLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loanId;
    private Date startLoanDate;
    private Date endLoanDate;
    private Date returnLoanDate;
    @Enumerated(EnumType.STRING)
    private CoverType coverType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "userId")
    private Member member;

    public HistoricalLoan(Date startLoanDate, Date endLoanDate, Date returnLoanDate, Member member, Title title, CoverType coverType) {
        this.startLoanDate = startLoanDate;
        this.endLoanDate = endLoanDate;
        this.returnLoanDate = returnLoanDate;
        this.member = member;
        this.title = title;
        this.coverType = coverType;
    }

    public HistoricalLoan() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="title_id", referencedColumnName = "titleId")
    private Title title;

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public Date getStartLoanDate() {
        return startLoanDate;
    }

    public void setStartLoanDate(Date startLoanDate) {
        this.startLoanDate = startLoanDate;
    }

    public Date getEndLoanDate() {
        return endLoanDate;
    }

    public void setEndLoanDate(Date endLoanDate) {
        this.endLoanDate = endLoanDate;
    }

    public Date getReturnLoanDate() {
        return returnLoanDate;
    }

    public void setReturnLoanDate(Date returnLoanDate) {
        this.returnLoanDate = returnLoanDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public CoverType getCoverType() {
        return coverType;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }
}
