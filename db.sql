create table EMPLOYEES (
  ID int,
  FIRSTNAME varchar2(20),
  LASTNAME varchar2(20),
  USERNAME varchar2(20),
  PASS varchar2(20),
  HAS_MANAGER number,
  IMAGE_PATH varchar2(100)
  );
  
alter table EMPLOYEES
add constraint employee_unique unique (USERNAME, PASS);

create table TEAM (
  ID int,
  TEAM_NAME varchar2(20)
);

create table EXPENSES (
 ID int,
 AMOUNT number,
 STATUS varchar2(20),
 DATE_SUBMITTED TIMESTAMP WITH LOCAL TIME ZONE,
 DATE_HANDLED TIMESTAMP WITH LOCAL TIME ZONE
);



-- make id in team table the primary key
alter table TEAM
  add constraint team_id primary key (ID);
-- add foreign key from employee to team
alter table EMPLOYEES
  add TEAM_ID number;
alter table EMPLOYEES add constraint fk_employee_from_team foreign key (TEAM_ID) references TEAM (ID) on delete cascade;

--make id in employees table the primary key
alter table EMPLOYEES
  add constraint employee_id primary key (ID);
--add foreign key from expenses to employees
alter table EXPENSES
  ADD EMPLOYEE_ID number;
alter table EXPENSES add constraint fk_expense_from_employee foreign key (EMPLOYEE_ID) references EMPLOYEES (ID) on delete cascade;

commit;

alter table EMPLOYEES 
  add EMAIL varchar2(20);

-- add foreign key to expenses to point to a team for manager's select all expenses
alter table EXPENSES 
  ADD TEAM_ID number;
-- connect foreign key in expenses to team's primary key id
commit;

CREATE SEQUENCE "SEQ_EMPLOYEES" MINVALUE 1 MAXVALUE 9999 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER employees_on_insert
  BEFORE INSERT ON EMPLOYEES
  FOR EACH ROW
BEGIN
  SELECT seq_employees.nextval
  INTO :new.id
  FROM dual;
END;
/

CREATE SEQUENCE "SEQ_TEAM_ID" MINVALUE 1 MAXVALUE 9999 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER team_on_insert
  BEFORE INSERT ON TEAM
  FOR EACH ROW
BEGIN
  SELECT SEQ_TEAM_ID.nextval
  INTO :new.id
  FROM dual;
END;
/

CREATE SEQUENCE "SEQ_EXPENSE_ID" MINVALUE 1 MAXVALUE 9999 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER expense_on_insert
  BEFORE INSERT ON EXPENSES
  FOR EACH ROW
BEGIN
  SELECT SEQ_EXPENSE_ID.nextval
  INTO :new.id
  FROM dual;
END;
/

COMMIT;

--FIRSTNAME
--LASTNAME
--USERNAME
--PASS
--HAS_MANAGER
--IMAGE_PATH
--TEAM_ID
--EMAIL

create or replace procedure ADD_EMPLOYEE (FIRSTNAME in varchar2, 
                                          LASTNAME in varchar2, 
                                          USERNAME in varchar2, 
                                          PASS in varchar2, 
                                          HAS_MANAGER in number,
                                          image in varchar2,
                                          team_id in number,
                                          EMAIL in varchar2
                                          ) 
as
begin
  insert into EMPLOYEES values (SEQ_EMPLOYEES.NEXTVAL, FIRSTNAME, LASTNAME, USERNAME, PASS, HAS_MANAGER, image, team_id, EMAIL);
end;
/
commit;

--ID
--AMOUNT
--STATUS
--DATE_SUBMITTED
--DATE_HANDLED
--EMPLOYEE_ID
--TEAM_ID

create or replace procedure add_expense (amount in number, e_id in number, t_id in number) 
as
begin
  insert into EXPENSES VALUES (SEQ_EXPENSE_ID.nextval, amount, NULL, CURRENT_TIMESTAMP, null, e_id, t_id);
end;
/

COMMIT;

create or replace procedure ADD_TEAM (t_name in varchar2)
as
begin 
  insert into TEAM values (SEQ_TEAM_ID.nextval, t_name);
end;
/

commit;

create or replace procedure approve_expense (approval in varchar2, e_id in number)
as
begin 
  update EXPENSES 
  set STATUS = approval
  where ID = e_id;
end;
/

commit;

exec add_team('JUSTICE LEAGUE');
exec add_employee('Bruce', 'Wayne', 'batman', 'gotham', null, null, 4, 'batman@batman.com', 'Philanthropist');
commit;

alter table EXPENSES
  ADD APPROVED_BY varchar2(20);
commit;

alter table EXPENSES
  add NAME varchar2(20);
alter table EXPENSES
  add DESCRIPTION varchar(100);

commit;

create or replace procedure approve_expense (approval in varchar2, exp_id in number, m_id in number, who_approved in varchar2)
as
begin 
  update EXPENSES 
  set STATUS = approval,
  DATE_HANDLED = CURRENT_TIMESTAMP,
  MANAGER_ID = m_id,
  APPROVED_BY = who_approved
  where ID = exp_id;
end;

create or replace procedure add_expense (amount in number, e_id in number, t_id in number, description in varchar2, name in varchar2) 
as
begin
  insert into EXPENSES VALUES (SEQ_EXPENSE_ID.nextval, amount, NULL, CURRENT_TIMESTAMP, null, e_id, t_id, null, null, description, name);
end;

commit;

exec approve_expense ('APPROVED', 8, 16, 'martian_manhunter');
commit;

select * from employees;

exec add_expense(25.00, 28, 4, 'Crosstown traffic', 'Uber');

exec add_expense(50.00, 28, 4, 'Best Western', 'Convention');

select * from EXPENSES;
commit;
savepoint;

SELECT USERNAME, EMAIL, IMAGE_PATH, FIRSTNAME, LASTNAME, expenses.*
  FROM EMPLOYEES
  RIGHT OUTER JOIN EXPENSES
  ON USERNAME = APPROVED_BY;
--  WHERE EXPENSES.employee_id = 10;
  
ALTER TABLE EXPENSES
  ADD RECEIPT_SRC VARCHAR(512);

COMMIT;
