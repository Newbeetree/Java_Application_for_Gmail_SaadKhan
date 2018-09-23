--EmailAdresses
insert into EmailAddresses (Email_Id, Name, Address)
values (1, 'Stephie Vergine', 'svergine0@ifeng.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (2, 'Letisha Cahalan', 'lcahalan1@ovh.net');
insert into EmailAddresses (Email_Id, Name, Address)
values (3, 'Skipper Danbi', 'sdanbi2@springer.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (4, 'Wylma Croll', 'wcroll3@nydailynews.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (5, 'Glynnis Waycott', 'gwaycott4@sciencedaily.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (6, 'Caressa Bousfield', 'cbousfield5@time.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (7, 'Candy Landman', 'clandman6@cargocollective.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (8, 'Heloise Charrisson', 'hcharrisson7@scribd.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (9, 'Rolf Norewood', 'rnorewood8@google.nl');
insert into EmailAddresses (Email_Id, Name, Address)
values (10, 'Malvin Tether', 'mtether9@acquirethisname.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (11, 'Tades Mc Coughan', 'tmca@merriam-webster.com');
insert into EmailAddresses (Email_Id, Name, Address) values (12, 'Cris Meach', 'cmeachb@ftc.gov');
insert into EmailAddresses (Email_Id, Name, Address)
values (13, 'Hugues Chillingsworth', 'hchillingsworthc@symantec.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (14, 'Adi Selliman', 'asellimand@blogtalkradio.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (15, 'Charity McNea', 'cmcneae@diigo.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (16, 'Margo Manjot', 'mmanjotf@redcross.org');
insert into EmailAddresses (Email_Id, Name, Address)
values (17, 'Gloriana Malec', 'gmalecg@china.com.cn');
insert into EmailAddresses (Email_Id, Name, Address)
values (18, 'Didi Lymer', 'dlymerh@e-recht24.de');
insert into EmailAddresses (Email_Id, Name, Address)
values (19, 'Ambrosi Franz-Schoninger', 'afranzschoningeri@godaddy.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (20, 'Orelia Wortman', 'owortmanj@soundcloud.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (21, 'Madella Killbey', 'mkillbeyk@ucsd.edu');
insert into EmailAddresses (Email_Id, Name, Address)
values (22, 'Larry Tides', 'ltiesl@forbes.com');
insert into EmailAddresses (Email_Id, Name, Address)
values (23, 'Yolanda Galia', 'ygaliam@irs.gov');
insert into EmailAddresses (Email_Id, Name, Address)
values (24, 'Minnaminnie Hadeke', 'mhadeken@soup.io');
insert into EmailAddresses (Email_Id, Name, Address)
values (25, 'Farrel Woodrup', 'fwoodrupo@theglobeandmail.com');
insert into emailaddresses (Email_Id, Name, Address)
values (26, 'users name', 'send.1633839@gmail.com');

--Folder
insert into Folder (Folder_Id, Folder_Name) values (1, 'Inbox');
insert into Folder (Folder_Id, Folder_Name) values (2, 'Spam');
insert into Folder (Folder_Id, Folder_Name) values (3, 'Trash');
insert into Folder (Folder_Id, Folder_Name) values (4, 'Sent');
insert into Folder (Folder_Id, Folder_Name) values (5, 'Starred');
insert into Folder (Folder_Id, Folder_Name) values (6, 'Unread');

--Emailbean
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (1, 26, 'Health',
        'Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi.',
        'sapien ut nunc vestibulum ante ipsum primis in faucibus orci', '2017-11-10 14:35:26',
        '2018-04-27 19:40:31', 3, 4);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (2, 26, 'Toys',
        'Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.',
        'aliquet at feugiat non pretium quis lectus suspendisse potenti in', '2018-01-16 14:25:32',
        '2018-07-07 11:00:04', 5, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (3, 26, 'Games',
        'Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.',
        'sem fusce consequat nulla nisl nunc nisl duis bibendum felis', '2017-10-05 12:19:28',
        '2018-04-05 11:58:08', 5, 3);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (4, 26, 'Outdoors',
        'Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi.',
        'vestibulum vestibulum ante ipsum primis in faucibus orci luctus et', '2018-01-03 11:55:38',
        '2018-04-30 04:08:30', 5, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (5, 26, 'Games',
        'In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet.',
        'ipsum integer a nibh in quis justo maecenas rhoncus aliquam', '2017-11-14 11:35:57',
        '2018-05-26 13:26:52', 5, 4);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (6, 26, 'Jewelery',
        'Sed ante. Vivamus tortor. Duis mattis egestas metus.',
        'sem praesent id massa id nisl venenatis lacinia aenean sit', '2017-10-04 05:10:00',
        '2018-02-06 20:40:57', 1, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (7, 26, 'Industrial',
        'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.',
        'ut tellus nulla ut erat id mauris vulputate elementum nullam', '2018-01-04 14:40:04',
        '2018-08-26 06:02:01', 2, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (8, 26, 'Health',
        'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.',
        'blandit non interdum in ante vestibulum ante ipsum primis in', '2018-01-13 10:39:56',
        '2018-05-25 14:00:54', 1, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values
  (9, 26, 'Computers', 'Sed ante. Vivamus tortor. Duis mattis egestas metus.',
   'in sagittis dui vel nisl duis ac nibh fusce lacus', '2017-10-13 06:55:09',
   '2018-07-13 14:00:38', 5, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (10, 26, 'Industrial',
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.',
        'lobortis sapien sapien non mi integer ac neque duis bibendum', '2017-10-03 19:47:05',
        '2018-08-15 18:50:47', 3, 4);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (11, 26, 'Industrial',
        'Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.',
        'mauris morbi non lectus aliquam sit amet diam in magna', '2017-11-09 10:18:57',
        '2018-07-13 06:59:09', 4, 4);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (12, 26, 'Home',
        'Phasellus in felis. Donec semper sapien a libero. Nam dui.',
        'dolor sit amet consectetuer adipiscing elit proin interdum mauris non',
        '2017-10-16 15:56:54', '2018-09-03 13:51:49', 4, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (13, 26, 'Jewelery',
        'Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi.',
        'primis in faucibus orci luctus et ultrices posuere cubilia curae', '2017-10-02 17:24:17',
        '2018-05-25 22:53:04', 4, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (14, 26, 'Electronics',
        'Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.',
        'lectus aliquam sit amet diam in magna bibendum imperdiet nullam', '2017-11-12 03:29:07',
        '2018-09-04 18:48:11', 5, 2);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (15, 26, 'Industrial',
        'Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.',
        'maecenas tincidunt lacus at velit vivamus vel nulla eget eros', '2018-01-11 05:10:05',
        '2018-03-08 02:00:21', 4, 3);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (16, 26, 'Automotive',
        'Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus.',
        'mattis egestas metus aenean fermentum donec ut mauris eget massa', '2017-12-21 21:31:29',
        '2018-09-20 04:13:36', 5, 3);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (17, 26, 'Shoes',
        'Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi.',
        'montes nascetur ridiculus mus etiam vel augue vestibulum rutrum rutrum',
        '2018-01-19 10:44:07', '2018-04-19 15:14:07', 3, 3);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (18, 26, 'Kids',
        'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.',
        'ut massa volutpat convallis morbi odio odio elementum eu interdum', '2017-12-18 19:16:06',
        '2018-06-01 16:33:59', 5, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values
  (19, 26, 'Health', 'Phasellus in felis. Donec semper sapien a libero. Nam dui.',
   'curabitur in libero ut massa volutpat convallis morbi odio odio', '2017-12-08 17:21:53',
   '2018-08-24 11:39:44', 1, 2);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (20, 26, 'Baby',
        'Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.',
        'mattis pulvinar nulla pede ullamcorper augue a suscipit nulla elit', '2017-12-20 19:51:57',
        '2018-03-27 11:05:57', 5, 4);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (21, 26, 'Automotive',
        'In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.',
        'eu felis fusce posuere felis sed lacus morbi sem mauris', '2017-11-08 11:45:54',
        '2018-08-29 08:13:11', 4, 2);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (22, 26, 'Baby',
        'In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet.',
        'est quam pharetra magna ac consequat metus sapien ut nunc', '2017-10-02 04:39:41',
        '2018-06-29 10:19:42', 1, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (23, 26, 'Automotive',
        'Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.',
        'tincidunt in leo maecenas pulvinar lobortis est phasellus sit amet', '2017-12-11 14:48:05',
        '2018-07-23 21:25:47', 3, 1);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (24, 26, 'Shoes',
        'Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.',
        'nibh ligula nec sem duis aliquam convallis nunc proin at', '2017-09-28 00:07:31',
        '2018-04-25 12:28:52', 1, 4);
insert into emailbean (Bean_id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id)
values (25, 26, 'Movies',
        'Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi.',
        'nullam molestie nibh in lectus pellentesque at nulla suspendisse potenti',
        '2018-01-03 00:35:49', '2018-04-10 13:30:54', 2, 1);

--EmailBeanAddress
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (6, 10, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (7, 17, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (2, 4, 'CC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (2, 17, 'CC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (2, 9, 'CC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (15, 7, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (20, 3, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (2, 4, 'CC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (12, 2, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (14, 18, 'BCC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (7, 19, 'BCC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (22, 15, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (23, 15, 'CC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (16, 22, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (8, 20, 'BCC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (14, 7, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (1, 9, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (21, 17, 'BCC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (6, 10, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (11, 12, 'BCC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (18, 13, 'CC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (12, 13, 'To');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (17, 8, 'BCC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (23, 16, 'CC');
insert into emailbeanadresses (Bean_Id, Email_Id, Email_Type) values (8, 14, 'To');

--attachments
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (1, 1, 'RutrumAcLobortis.mp3', true);
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (2, 3, 'TemporConvallis.ppt', false);
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (3, 5, 'BibendumImperdietNullam.jpg', false);
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (4, 14, 'AliquamConvallis.avi', true);
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (5, 17, 'DuisBibendumFelis.doc', false);
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (6, 13, 'Praesent.png', true);
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (7, 25, 'Nec.pdf', false);
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (8, 18, 'ConsectetuerAdipiscing.png', true);
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (9, 19, 'LobortisVelDapibus.ppt', false);
insert into attachments (Attach_id, Email_Id, File_Name, File_Type)
values (10, 8, 'ErosViverraEget.gif', true);