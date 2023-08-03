--
-- PostgreSQL database dump
--

-- Dumped from database version 8.4.22
-- Dumped by pg_dump version 14.2

-- Started on 2023-08-03 21:19:16

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET escape_string_warning = off;
SET row_security = off;

DROP DATABASE "Final_Project";
--
-- TOC entry 1808 (class 1262 OID 82620)
-- Name: Final_Project; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "Final_Project" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';


ALTER DATABASE "Final_Project" OWNER TO postgres;

\connect "Final_Project"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET escape_string_warning = off;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 1809 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

--
-- TOC entry 141 (class 1259 OID 82628)
-- Name: balance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.balance (
    id bigint NOT NULL,
    balance bigint NOT NULL
);


ALTER TABLE public.balance OWNER TO postgres;

--
-- TOC entry 140 (class 1259 OID 82626)
-- Name: balance_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.balance_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.balance_id_seq OWNER TO postgres;

--
-- TOC entry 1811 (class 0 OID 0)
-- Dependencies: 140
-- Name: balance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.balance_id_seq OWNED BY public.balance.id;


--
-- TOC entry 143 (class 1259 OID 82799)
-- Name: operation_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.operation_list (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    operation_type_number integer NOT NULL,
    operation_type_name character varying NOT NULL,
    operation_time timestamp with time zone NOT NULL
);


ALTER TABLE public.operation_list OWNER TO postgres;

--
-- TOC entry 142 (class 1259 OID 82797)
-- Name: operation_list_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.operation_list_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.operation_list_id_seq OWNER TO postgres;

--
-- TOC entry 1812 (class 0 OID 0)
-- Dependencies: 142
-- Name: operation_list_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.operation_list_id_seq OWNED BY public.operation_list.id;


--
-- TOC entry 145 (class 1259 OID 82815)
-- Name: transfer_operations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transfer_operations (
    id bigint NOT NULL,
    usersenderid bigint NOT NULL,
    userreceiverid bigint NOT NULL,
    transfermoney bigint NOT NULL,
    transfer_operation_time timestamp with time zone NOT NULL
);


ALTER TABLE public.transfer_operations OWNER TO postgres;

--
-- TOC entry 144 (class 1259 OID 82813)
-- Name: transfer_operations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transfer_operations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transfer_operations_id_seq OWNER TO postgres;

--
-- TOC entry 1813 (class 0 OID 0)
-- Dependencies: 144
-- Name: transfer_operations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transfer_operations_id_seq OWNED BY public.transfer_operations.id;


--
-- TOC entry 1699 (class 2604 OID 82631)
-- Name: balance id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.balance ALTER COLUMN id SET DEFAULT nextval('public.balance_id_seq'::regclass);


--
-- TOC entry 1700 (class 2604 OID 82802)
-- Name: operation_list id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operation_list ALTER COLUMN id SET DEFAULT nextval('public.operation_list_id_seq'::regclass);


--
-- TOC entry 1701 (class 2604 OID 82818)
-- Name: transfer_operations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transfer_operations ALTER COLUMN id SET DEFAULT nextval('public.transfer_operations_id_seq'::regclass);


--
-- TOC entry 1798 (class 0 OID 82628)
-- Dependencies: 141
-- Data for Name: balance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.balance (id, balance) FROM stdin;
3	1000
5	12345
2	2005200
1	2300
4	9845
\.


--
-- TOC entry 1800 (class 0 OID 82799)
-- Dependencies: 143
-- Data for Name: operation_list; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.operation_list (id, user_id, operation_type_number, operation_type_name, operation_time) FROM stdin;
1	1	1	GET_BALANCE	2023-07-25 21:38:35.463+04
2	2	1	GET_BALANCE	2023-07-25 21:38:51.601+04
3	1	1	GET_BALANCE	2023-07-25 21:39:19.368+04
4	1	2	PUT_MONEY	2023-07-25 21:39:19.402+04
5	2	2	PUT_MONEY	2023-07-25 21:44:41.198+04
6	1	1	GET_BALANCE	2023-07-27 20:16:43.102+04
7	2	1	GET_BALANCE	2023-07-27 20:16:47.479+04
8	1	1	GET_BALANCE	2023-07-30 02:44:03.121+04
10	1	1	GET_BALANCE	2023-07-30 15:34:29.418+04
13	1	1	GET_BALANCE	2023-07-30 16:02:12.065+04
14	1	1	GET_BALANCE	2023-07-30 16:04:40.911+04
15	1	1	GET_BALANCE	2023-07-30 16:05:08.982+04
16	1	2	PUT_MONEY	2023-07-30 16:05:08.99+04
17	1	1	GET_BALANCE	2023-07-30 16:06:08.365+04
18	1	2	PUT_MONEY	2023-07-30 16:06:08.368+04
19	1	1	GET_BALANCE	2023-07-30 16:06:19.719+04
20	1	1	GET_BALANCE	2023-07-30 16:06:32.115+04
21	1	2	PUT_MONEY	2023-07-30 16:06:32.119+04
22	1	1	GET_BALANCE	2023-07-30 16:06:35.671+04
23	1	1	GET_BALANCE	2023-07-30 16:08:16.259+04
24	1	2	PUT_MONEY	2023-07-30 16:08:16.336+04
25	1	1	GET_BALANCE	2023-07-30 16:13:40.804+04
26	1	3	TAKE_MONEY	2023-07-30 16:13:40.863+04
27	1	1	GET_BALANCE	2023-07-30 16:14:57.051+04
28	1	3	TAKE_MONEY	2023-07-30 16:14:57.106+04
29	1	1	GET_BALANCE	2023-07-30 16:17:38.272+04
30	1	3	TAKE_MONEY	2023-07-30 16:17:38.276+04
31	2	1	GET_BALANCE	2023-07-30 16:17:38.375+04
32	2	2	PUT_MONEY	2023-07-30 16:17:38.377+04
33	1	4	TRANSFER_MONEY	2023-07-30 16:17:38.382+04
34	2	1	GET_BALANCE	2023-07-30 16:17:38.494+04
35	2	2	PUT_MONEY	2023-07-30 16:17:38.497+04
36	1	1	GET_BALANCE	2023-07-30 16:19:13.961+04
37	1	1	GET_BALANCE	2023-07-30 16:19:14.177+04
38	2	1	GET_BALANCE	2023-07-30 16:19:22.412+04
39	2	2	PUT_MONEY	2023-07-30 16:19:22.414+04
40	1	1	GET_BALANCE	2023-07-30 16:19:30.629+04
41	2	1	GET_BALANCE	2023-07-30 16:19:30.722+04
42	2	2	PUT_MONEY	2023-07-30 16:19:30.726+04
43	1	1	GET_BALANCE	2023-07-30 16:19:30.814+04
44	2	1	GET_BALANCE	2023-07-30 16:19:30.906+04
45	2	2	PUT_MONEY	2023-07-30 16:19:30.909+04
46	1	2	PUT_MONEY	2023-07-31 12:05:50.756+04
47	1	3	TAKE_MONEY	2023-07-31 12:06:19.253+04
48	2	3	TAKE_MONEY	2023-07-31 12:06:46.318+04
49	2	4	TRANSFER_MONEY	2023-07-31 12:06:46.322+04
50	1	2	PUT_MONEY	2023-07-31 12:06:46.324+04
51	1	2	PUT_MONEY	2023-07-31 14:51:32.391+04
52	1	3	TAKE_MONEY	2023-07-31 14:51:44.377+04
53	1	3	TAKE_MONEY	2023-08-01 14:49:31.436+04
54	1	4	TRANSFER_MONEY	2023-08-01 14:49:31.473+04
55	2	2	PUT_MONEY	2023-08-01 14:49:31.476+04
56	2	3	TAKE_MONEY	2023-08-01 14:51:01.602+04
57	2	4	TRANSFER_MONEY	2023-08-01 14:51:01.619+04
58	1	2	PUT_MONEY	2023-08-01 14:51:01.621+04
59	2	3	TAKE_MONEY	2023-08-01 14:51:59.161+04
60	2	4	TRANSFER_MONEY	2023-08-01 14:51:59.203+04
61	1	2	PUT_MONEY	2023-08-01 14:51:59.207+04
62	1	3	TAKE_MONEY	2023-08-01 14:52:58.647+04
63	1	4	TRANSFER_MONEY	2023-08-01 14:52:58.648+04
64	2	2	PUT_MONEY	2023-08-01 14:52:58.652+04
65	1	3	TAKE_MONEY	2023-08-01 14:53:02.663+04
66	1	4	TRANSFER_MONEY	2023-08-01 14:53:02.664+04
67	2	2	PUT_MONEY	2023-08-01 14:53:02.668+04
68	2	3	TAKE_MONEY	2023-08-01 14:53:25.76+04
69	2	4	TRANSFER_MONEY	2023-08-01 14:53:25.762+04
70	1	2	PUT_MONEY	2023-08-01 14:53:25.765+04
\.


--
-- TOC entry 1802 (class 0 OID 82815)
-- Dependencies: 145
-- Data for Name: transfer_operations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transfer_operations (id, usersenderid, userreceiverid, transfermoney, transfer_operation_time) FROM stdin;
1	2	1	1000	2023-08-01 14:51:59.212+04
2	1	2	1000	2023-08-01 14:52:58.655+04
3	1	2	1000	2023-08-01 14:53:02.671+04
4	2	1	2000	2023-08-01 14:53:25.768+04
\.


--
-- TOC entry 1814 (class 0 OID 0)
-- Dependencies: 140
-- Name: balance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.balance_id_seq', 5, true);


--
-- TOC entry 1815 (class 0 OID 0)
-- Dependencies: 142
-- Name: operation_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.operation_list_id_seq', 70, true);


--
-- TOC entry 1816 (class 0 OID 0)
-- Dependencies: 144
-- Name: transfer_operations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transfer_operations_id_seq', 4, true);


--
-- TOC entry 1705 (class 2606 OID 82807)
-- Name: operation_list operation_list_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operation_list
    ADD CONSTRAINT operation_list_pkey PRIMARY KEY (id);


--
-- TOC entry 1703 (class 2606 OID 82633)
-- Name: balance pk_balance; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.balance
    ADD CONSTRAINT pk_balance PRIMARY KEY (id);


--
-- TOC entry 1707 (class 2606 OID 82820)
-- Name: transfer_operations transfer_operations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transfer_operations
    ADD CONSTRAINT transfer_operations_pkey PRIMARY KEY (id);


--
-- TOC entry 1708 (class 2606 OID 82808)
-- Name: operation_list operation_list_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operation_list
    ADD CONSTRAINT operation_list_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.balance(id);


--
-- TOC entry 1710 (class 2606 OID 82826)
-- Name: transfer_operations transfer_operations_userreceiverid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transfer_operations
    ADD CONSTRAINT transfer_operations_userreceiverid_fkey FOREIGN KEY (userreceiverid) REFERENCES public.balance(id);


--
-- TOC entry 1709 (class 2606 OID 82821)
-- Name: transfer_operations transfer_operations_usersenderid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transfer_operations
    ADD CONSTRAINT transfer_operations_usersenderid_fkey FOREIGN KEY (usersenderid) REFERENCES public.balance(id);


--
-- TOC entry 1810 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-08-03 21:19:16

--
-- PostgreSQL database dump complete
--

