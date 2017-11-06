drop table GDP_ItemReviewRecord;
create table GDP_ItemReviewRecord( ---ItemReviewRecord
       tid number(10) not null,
       changenumber varchar2(50) not null,----Change_ID
       itemid number(10) , ---Item_ID
       userid varchar2(50) not null, ---UserID
       username varchar2(100) , ---UserID
       ItemNumber varchar2(50)  not null, ---Item_Number
       SpecReview varchar2(50), ---SpecReview
       SpecReviewValue varchar2(100),
       Reason varchar2(1000), ----Reason
       docid number(10),     ----Document
       DocNumber varchar2(30),
       ecnid number(10),      ----ECN
       ECNNumber varchar2(30),
       primary key(changenumber,ItemNumber,userid)
);
drop sequence GDP_ItemReviewRecord_seq_id;
create sequence GDP_ItemReviewRecord_seq_id increment by 1 start with 1 nomaxvalue nominvalue nocache;

drop table GDP_ManagerReview;
create  table GDP_ManagerReview( ---
       tid number(10) not null,
      changenumber varchar2(50) not null,
       userid varchar2(50) not null, ---Managerid
       itemid number(10) ,
       ItemNumber varchar2(50)  not null, ---Item_Number
       managerReview varchar2(1000),       
       primary key(changenumber,userid,ItemNumber)
);

drop sequence GDP_managerReview_seq_id;
create sequence GDP_managerReview_seq_id increment by 1 start with 1 nomaxvalue nominvalue nocache;

drop table  GDP_ManagerApprove;
create table GDP_ManagerApprove(
       tid number(10) not null,
      changenumber varchar2(50) not null,
      userid varchar2(50) not null,  ----Managerid
       managerApprove varchar2(50), 
      ---- managerComment varchar2(1000), 
       primary key(changenumber,userid)
);
drop  sequence GDP_ManagerApprove_seq_id;
create sequence GDP_ManagerApprove_seq_id increment by 1 start with 1 nomaxvalue nominvalue nocache;


