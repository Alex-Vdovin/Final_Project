--
-- PostgreSQL database dump
--

-- Dumped from database version 8.4.22
-- Dumped by pg_dump version 14.2

-- Started on 2023-07-31 14:11:00

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
-- TOC entry 1795 (class 1262 OID 82620)
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
-- TOC entry 1796 (class 0 OID 0)
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
-- TOC entry 1798 (class 0 OID 0)
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
-- TOC entry 1799 (class 0 OID 0)
-- Dependencies: 142
-- Name: operation_list_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.operation_list_id_seq OWNED BY public.operation_list.id;


--
-- TOC entry 1693 (class 2604 OID 82631)
-- Name: balance id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.balance ALTER COLUMN id SET DEFAULT nextval('public.balance_id_seq'::regclass);


--
-- TOC entry 1694 (class 2604 OID 82802)
-- Name: operation_list id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operation_list ALTER COLUMN id SET DEFAULT nextval('public.operation_list_id_seq'::regclass);


--
-- TOC entry 1787 (class 0 OID 82628)
-- Dependencies: 141
-- Data for Name: balance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.balance (id, balance) FROM stdin;
3	1000
5	12345
4	9845
2	2006200
1	1300
\.


--
-- TOC entry 1789 (class 0 OID 82799)
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
\.


--
-- TOC entry 1800 (class 0 OID 0)
-- Dependencies: 140
-- Name: balance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.balance_id_seq', 5, true);


--
-- TOC entry 1801 (class 0 OID 0)
-- Dependencies: 142
-- Name: operation_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.operation_list_id_seq', 52, true);


--
-- TOC entry 1698 (class 2606 OID 82807)
-- Name: operation_list operation_list_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operation_list
    ADD CONSTRAINT operation_list_pkey PRIMARY KEY (id);


--
-- TOC entry 1696 (class 2606 OID 82633)
-- Name: balance pk_balance; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.balance
    ADD CONSTRAINT pk_balance PRIMARY KEY (id);


--
-- TOC entry 1699 (class 2606 OID 82808)
-- Name: operation_list operation_list_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operation_list
    ADD CONSTRAINT operation_list_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.balance(id);


--
-- TOC entry 1797 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-07-31 14:11:01

--
-- PostgreSQL database dump complete
--

