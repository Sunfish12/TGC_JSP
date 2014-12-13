drop table PrivateGroupGamesItem;
drop table PrivateChoiceGames;
drop table PrivateGroupRoomMessage;
drop table JoinerInfoPrivateGroupRoom;
drop table PrivateGroupRoomInfo
drop table PrivateGroupRoom;
drop table StoreGroupChoiceGames;
drop table StoreGroupRoomMessage;
drop table JoinerInfoStoreGroup;
drop table StoreGroupRoomInfo;
drop table StoreGroupRoom;
drop table GroupChoiceGames;
drop table GroupRoomMessage;
drop table Joiner_Info;
drop table GroupRoomInfo;
drop table GroupRoom;
drop table BoardGamesImage;
drop table BoardGames;
drop table BoardGameKind;
drop table StoreScore;
drop table RentalTime;
drop table StoreInformationImage;
drop table StoreInformation;
drop table TabuUsernameTable;
drop table MemberFavoredType;
drop table StoreMember;
drop table Administrator;
drop table Member;



--create 會員Member--
create table Member (
 memberId								  int IDENTITY (1,1) not null,
 username								  varchar(30) not null, 
 pswd									  varbinary(50) not null,
 email									  varchar(50) not null,
 lastname								  varchar(20) not null,
 firstname								  varchar(20) not null,
 gender									  varchar(10),
 nickname								  nvarchar(20),
 birthday								  datetime,
 idCard									  varchar(20) not null,
 joinDate								  datetime,
 phone									  varchar(50),
 memberAddress							  varchar(100),
 imgFileName							  varchar(50),
 memberImage							  image,
 isGroupBan								  bit,
 isCommentBan							  bit,
 notBanTime								  datetime,
 banTime								  datetime,
constraint Member_primary_key primary key (username));

--create管理員	Administrator--
create table Administrator(
adminUsername    						  varchar(30),
adminPswd								  varbinary(50),
imgFileName								  varchar(50),
adminMemberImage						  image,
constraint Administrator_primary_key primary key (adminUsername));

--create店家會員	StoreMember--
create table StoreMember (
storeMemberId							  int IDENTITY (1,1)not null,
storeUsername							  varchar(30),
storePswd								  varbinary(50),
storeJoinDate							  datetime,
storePhone								  varchar(50),
imgFileName								  varchar(50),
storeImage								  image,
storeEmail								  varchar(50),
storeWebsite							  varchar(100),
constraint StoreMember_primary_key primary key (storeUsername));


--create會員喜好桌遊類型MemberFavoredType--
create table MemberFavoredType(
username								  varchar(30),
favoredType								  varchar(30),
constraint MemberFavoredType_username_fk foreign key (username) references Member(username));

--create 檢舉名單審核表	BlackUsernameTable--
create table TabuUsernameTable(
tabuId									  int IDENTITY (1,1),
tabuUsername							  varchar(30),
toTabuUsername							  varchar(30),
tabuReason								  varchar(400),
constraint BlackUsernameTable_blackUsername_fk foreign key (tabuUsername) references Member(username),
constraint BlackUsernameTable_toBlackUsername_fk foreign key (toTabuUsername) references Member(username));

--create 專賣店資訊	StoreInformation--
create table StoreInformation(
storeUsername							  varchar(30),
storeId									  int IDENTITY (1,1),
storeName								  varchar(30),
storeAddress							  varchar(100),
imgFileName								  varchar(50),
storeImage								  image,
storeTel								  varchar(50),
rentAreaCost							  float,
groupUpperLimit							  int,
constraint StoreInformation_storeMemberId_fk foreign key (storeUsername) references StoreMember(storeUsername),
constraint StoreInformation_primary_key primary key (storeId));

--create 專賣店圖片	StoreInformationImage--
create table StoreInformationImage(
storeId      							  int,
storeImageId							  int IDENTITY (1,1),
boradGameHelp							  varchar(MAX),
imgFileName								  varchar(50),
areaImage								  image,
constraint StoreInformationImage_storeId_fk foreign key (storeId) references StoreInformation(storeId),
constraint StoreInformationImage_primary_key primary key (storeImageId));

--create 專賣店場地時段	RentalTime--
create table RentalTime(
storeId           						  int,
monStart								  datetime,
monEnd									  datetime,
tueStart								  datetime,
tueEnd									  datetime,
wedStart								  datetime,
wedEnd									  datetime,
thuStary								  datetime,
thuEnd									  datetime,
friStart								  datetime,
friEnd									  datetime,
satStart								  datetime,
satEnd									  datetime,
sunStart								  datetime,
sunEnd									  datetime,
constraint RentalTime_storeId_fk foreign key (storeId) references StoreInformation(storeId));

--專賣店評分	StoreScore--
create table StoreScore(
storeId									  int,
username								  varchar(30),
storeScore								  float,
storeScoreReason						  varchar(400),
constraint StoreScore_storeId_fk foreign key (storeId) references StoreInformation(storeId));

--桌遊類型	BoardGameKind
create table BoardGameKind(
boardGameNumber							  int,
boardGameStyle							  varchar(10),
constraint BoardGameKind_primary_key primary key (boardGameStyle));

--(專賣店)桌遊資訊	BoardGames--
create table BoardGames(
boardGamesId							  int IDENTITY (1,1),
storeUsername						      varchar(30),
storeName							      varchar(30),
boardGameName							  varchar(50),
boardGameStyle							  varchar(10),
boardGameNumber							  int,
imgFileName								  varchar(50),
boardGameImage							  image,
boardGameExplan							  varchar(MAX),
constraint BoardGames_primary_key primary key (boardGamesId),
constraint BoardGames_storeUsername_fk foreign key (storeUsername) references StoreMember (storeUsername),
constraint BoardGames_boardGameStyle_fk foreign key (boardGameStyle) references BoardGameKind (boardGameStyle));

--桌遊圖片	BoardGamesImage--
create table BoardGamesImage(
boardGamesId							  int,
storeImageId							  int IDENTITY (1,1),
imgFileName								  varchar(50),
boardGameImages					          image,
constraint BoardGamesImage_boardGamesId_fk foreign key (boardGamesId) references BoardGames (boardGamesId),
constraint BoardGamesImage_primary_key primary key (storeImageId));

--私人房間(店家場)	GroupRoom--
create table GroupRoom(
groupSerialNumber						  int IDENTITY (1,1),
storeUsername							  varchar(50),
storeName								  varchar(30),
groupUsername							  varchar(30),
groupStartTime							  datetime,
groupEndTime							  datetime,
groupRoomName							  varchar(20),
groupSuggestNumber						  int,
groupLowerLimit							  int,
groupUpperLimit							  int,
groupGameTime				              datetime,
reserveGroupStartTime					  datetime,
reserveGroupEndTime			  			  datetime,
roomState								  int,
imgFileName	                              varchar(50),
privateGroupImage						  image,
constraint GroupRoom_primary_key primary key (groupSerialNumber),
constraint GroupRoom_groupUsername_fk foreign key (groupUsername) references Member (username));

--私人房間資訊(店家場)	GroupRoomInfo--
create table GroupRoomInfo(
groupSerialNumber						  int,
groupPicture							  image,
imgFileName	                              varchar(50),
groupPictureSerialNumber				  int,
constraint GroupRoomInfo_groupSerialNumber_fk foreign key (groupSerialNumber) references GroupRoom (groupSerialNumber),
constraint GroupRoomInfo_primary_key primary key (groupPictureSerialNumber));


--入團者(私人房間店家場)資訊 	Joiner_Info
create table Joiner_Info(
groupSerialNumber						  int,
joinTime								  datetime,
username			    				  varchar(30),
constraint Joiner_Info_groupSerialNumber_fk foreign key (groupSerialNumber) references GroupRoom (groupSerialNumber));

--私人房間(店家場)留言	GroupRoomMessage
create table GroupRoomMessage(
GroupRoomMessageSerialNumber			  int,
groupSerialNumber						  int,
messageUsername							  varchar(30),
messageContents							  varchar(400),
messageTime							      dateTime,
constraint GroupRoomMessage_groupSerialNumber_fk foreign key (groupSerialNumber) references GroupRoom (groupSerialNumber));

--私人房間(店家場)所選桌遊	GroupChoiceGames
create table GroupChoiceGames(
choiceGamesSerialNumber					  int,
groupSerialNumber						  int,
boardGameStyle							  varchar(10),
boardGameName							  varchar(50),
constraint GroupChoiceGames_primary_key primary key (choiceGamesSerialNumber),
constraint GroupChoiceGames_groupSerialNumber_fk foreign key (groupSerialNumber) references GroupRoom (groupSerialNumber),
constraint GroupChoiceGames_boardGameStyle_fk foreign key (boardGameStyle) references BoardGameKind (boardGameStyle));

--店家活動	StoreGroupRoom
create table StoreGroupRoom(
groupSerialNumber						  int IDENTITY (1,1),
storeUsername							  varchar(50),
storeId									  int,
groupUsername							  varchar(30),
groupTime								  datetime,
groupRoomName							  varchar(20),
groupSuggestNumber						  int,
groupLowerLimit							  int,
groupUpperLimit							  int,
groupGameTime							  int,
ActicityStartTime						  datetime,
ActicityEndTime							  datetime,
constraint StoreGroupRoom_primary_key primary key (groupSerialNumber),
constraint StoreGroupRoom_groupUsername_fk foreign key (groupUsername) references Member (username));

--店家活動資訊	StoreGroupRoomInfo
create table StoreGroupRoomInfo(
groupSerialNumber						  int,
imgFileName	                              varchar(50),
activityPicture							  image,
PictureSerialNumber						  int IDENTITY (1,1),
constraint StoreGroupRoomInfo_primary_key primary key (PictureSerialNumber),
constraint StoreGroupRoomInfo_groupSerialNumber_fk foreign key (groupSerialNumber) references StoreGroupRoom (groupSerialNumber));

--入團者(店家活動資訊)	JoinerInfoStoreGroup
create table JoinerInfoStoreGroup(
groupSerialNumber						  int,
joinTime								  datetime,
username								  varchar(30),
constraint JoinerInfoStoreGroup_groupSerialNumber_fk foreign key (groupSerialNumber) references StoreGroupRoom (groupSerialNumber));

--店家活動留言	StoreGroupRoomMessage
create table StoreGroupRoomMessage(
StoreGroupRoomMessageSerialNumber		  int IDENTITY (1,1),
groupSerialNumber						  int,
storeUsername							  varchar(30),
messageUsername							  varchar(30),
messageContents							  varchar(400),
messageTime								  dateTime,
constraint StoreGroupRoomMessage_groupSerialNumber_fk foreign key (groupSerialNumber) references StoreGroupRoom (groupSerialNumber));

--店家房間(店家場)所選桌遊	StoreGroupChoiceGames
create table StoreGroupChoiceGames(
choiceGamesSerialNumber					  int IDENTITY (1,1),
groupSerialNumber						  int,
boardGameStyle							  varchar(10),
boardGameName							  varchar(50),
constraint StoreGroupChoiceGames_primary_key primary key (choiceGamesSerialNumber),
constraint StoreGroupChoiceGames_groupSerialNumber_fk foreign key (groupSerialNumber) references StoreGroupRoom (groupSerialNumber),
constraint StoreGroupChoiceGames_boardGameStyle_fk foreign key (boardGameStyle) references BoardGameKind (boardGameStyle));


--create私人房間私人場--
create table PrivateGroupRoom(
privateGroupSerialNumber				  int IDENTITY (1,1),
privateGroupUsername					  varchar(30),
privateGroupRoomName					  varchar(20),
boardGameStyle							  varchar(10),
privateGroupSuggestNumber				  int,
privateGroupUpperLimit	                  int,
privateGroupLocation					  varchar(20),
privateGroupAddress						  varchar(100),
privateGroupCost						  float,
privateGroupExplain						  varchar(MAX),
privateGroupStartTime					  datetime,
imgFileName	                              varchar(50),
privateGroupImage						  image,
privateGroupInformation					  varchar(MAX)
constraint PrivateGroupRoom_primary_key primary key (privateGroupSerialNumber),
constraint PrivateGroupRoom_privateGroupUsername_fk foreign key (privateGroupUsername) references Member(username),
constraint PrivateGroupRoom_boardGameStyle_fk foreign key (boardGameStyle) references BoardGameKind(boardGameStyle));

--私人房間資訊(私人場)	PrivateGroupRoomInfo
create table PrivateGroupRoomInfo(
privateGroupSerialNumber				  int,
imgFileName	                              varchar(50),
privateGroupPicture						  image,
privateGroupPictureSerialNumber			  int IDENTITY (1,1),
constraint PrivateGroupRoomInfo_primary_key primary key (privateGroupPictureSerialNumber),
constraint PrivateGroupRoomInfo_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber));

--入團者(私人房間私人場)資訊	JoinerInfoPrivateGroupRoom
create table JoinerInfoPrivateGroupRoom(
privateGroupSerialNumber				  int,
joinTime								  datetime,
username								  varchar(30),
constraint JoinerInfoPrivateGroupRoom_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber));

--私人房間(私人場)留言	PrivateGroupRoomMessage
create table PrivateGroupRoomMessage(
PrivateGroupRoomMessageSerialNumber		  int IDENTITY (1,1),
privateGroupSerialNumber				  int,
messageUsername							  varchar(30),
messageContents							  varchar(400),
messageTime								  dateTime,
constraint PrivateGroupRoomMessage_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber));

--私人房間(私人場)所選桌遊	PrivateChoiceGames
create table PrivateChoiceGames(
choiceGamesSerialNumber					  int IDENTITY (1,1),
privateGroupSerialNumber				  int,
boardGameStyle							  varchar(10),
boardGameName							  varchar(50),
constraint PrivateChoiceGames_primary_key primary key (choiceGamesSerialNumber),
constraint PrivateChoiceGames_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber),
constraint PrivateChoiceGames_boardGameStyle_fk foreign key (boardGameStyle) references BoardGameKind(boardGameStyle));

--私人團遊玩項目 	PrivateGroupGamesItem
create table PrivateGroupGamesItem(
privateGroupSerialNumber				  int,
privateGroupGames						  varchar(20),
constraint PrivateGroupGamesItem_privateGroupSerialNumber_fk foreign key (privateGroupSerialNumber) references PrivateGroupRoom(privateGroupSerialNumber));
