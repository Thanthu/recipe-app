create table category (id  bigserial not null, description varchar(255), primary key (id))
create table ingredient (id  bigserial not null, amount numeric(19, 2), description varchar(255), recipe_id int8, unit_of_measure_id int8, primary key (id))
create table notes (id  bigserial not null, recipe_notes text, recipe_id int8, primary key (id))
create table recipe (id  bigserial not null, cook_time int4, description varchar(255), difficulty varchar(255), directions text, image oid, prep_time int4, servings int4, source varchar(255), url varchar(255), notes_id int8, primary key (id))
create table recipe_category (recipe_id int8 not null, category_id int8 not null, primary key (recipe_id, category_id))
create table unit_of_measure (id  bigserial not null, unit varchar(255), primary key (id))
alter table ingredient add constraint FKj0s4ywmqqqw4h5iommigh5yja foreign key (recipe_id) references recipe
alter table ingredient add constraint FK15ttfoaomqy1bbpo251fuidxw foreign key (unit_of_measure_id) references unit_of_measure
alter table notes add constraint FKdbfsiv21ocsbt63sd6fg0t3c8 foreign key (recipe_id) references recipe
alter table recipe add constraint FK37al6kcbdasgfnut9xokktie9 foreign key (notes_id) references notes
alter table recipe_category add constraint FKqsi87i8d4qqdehlv2eiwvpwb foreign key (category_id) references category
alter table recipe_category add constraint FKcqlqnvfyarhieewfeayk3v25v foreign key (recipe_id) references recipe
