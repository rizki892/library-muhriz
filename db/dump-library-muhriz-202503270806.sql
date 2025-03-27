--
-- PostgreSQL database dump
--

-- Dumped from database version 16.6
-- Dumped by pg_dump version 16.6

-- Started on 2025-03-27 08:06:22

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4859 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16812)
-- Name: books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.books (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by character varying(255),
    updated_at timestamp(6) without time zone NOT NULL,
    updated_by character varying(255),
    author character varying(255),
    status character varying(255),
    title character varying(255),
    deleted_at timestamp(6) without time zone,
    CONSTRAINT books_status_check CHECK (((status)::text = ANY ((ARRAY['AVAILABLE'::character varying, 'BORROWED'::character varying])::text[])))
);


ALTER TABLE public.books OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16850)
-- Name: loans; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.loans (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by character varying(255),
    updated_at timestamp(6) without time zone NOT NULL,
    updated_by character varying(255),
    due_date date,
    loan_date date,
    return_date date,
    status character varying(255) NOT NULL,
    book_id uuid NOT NULL,
    user_id uuid NOT NULL,
    CONSTRAINT loans_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'ACCEPTED'::character varying, 'OVERDUE'::character varying, 'RETURNED'::character varying])::text[])))
);


ALTER TABLE public.loans OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16828)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by character varying(255),
    updated_at timestamp(6) without time zone NOT NULL,
    updated_by character varying(255),
    email character varying(255),
    name character varying(255),
    password character varying(255),
    role character varying(255),
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'USER'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 4851 (class 0 OID 16812)
-- Dependencies: 215
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.books (id, created_at, created_by, updated_at, updated_by, author, status, title, deleted_at) FROM stdin;
442d1022-6d6a-4dde-82a1-1b739d48944a	2025-03-26 12:12:15.482	anonymousUser	2025-03-26 12:12:15.482	anonymousUser	J.K. Rowling	AVAILABLE	Harry Potter	\N
2aa5200b-ba52-4e67-91d1-b6dfe8b2a651	2025-03-26 13:48:18.779	anonymousUser	2025-03-26 13:48:18.779	anonymousUser	Pramoedya Ananta Toer	AVAILABLE	Bumi Manusia	\N
7f4c360a-9728-48ea-b65a-b13d6904148d	2025-03-26 13:49:06.074	anonymousUser	2025-03-26 13:49:06.074	anonymousUser	Kristen Sosulski	AVAILABLE	Data Visualization Made Simple	\N
8170636f-84b4-442c-95bc-81f385985035	2025-03-26 13:49:38.275	anonymousUser	2025-03-26 13:49:38.275	anonymousUser	Tracy Kidder	AVAILABLE	The Soul of a New Machine	\N
51ad132c-b648-42db-89d4-090ecdcde7ed	2025-03-26 13:47:29.452	anonymousUser	2025-03-26 13:57:55.816	anonymousUser	Tracy Kidder	AVAILABLE	The Soul of a New Machine	2025-03-26 13:57:55.8149
7c4b675a-c1c3-4040-8484-9266c8dd0c30	2025-03-26 14:54:41.186	john@example.com	2025-03-26 14:54:41.186	john@example.com	tes	AVAILABLE	tes	\N
\.


--
-- TOC entry 4853 (class 0 OID 16850)
-- Dependencies: 217
-- Data for Name: loans; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.loans (id, created_at, created_by, updated_at, updated_by, due_date, loan_date, return_date, status, book_id, user_id) FROM stdin;
091e63f8-ed1e-4820-98b1-80a20faef75f	2025-03-26 15:08:37.28	john@example.com	2025-03-27 07:55:01.46	john@example.com	2025-04-09	2025-03-26	\N	ACCEPTED	7c4b675a-c1c3-4040-8484-9266c8dd0c30	e3842372-5b51-4c40-80cd-daf39040a96a
\.


--
-- TOC entry 4852 (class 0 OID 16828)
-- Dependencies: 216
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_at, created_by, updated_at, updated_by, email, name, password, role) FROM stdin;
e3842372-5b51-4c40-80cd-daf39040a96a	2025-03-26 11:04:14.193	anonymousUser	2025-03-26 11:04:14.193	anonymousUser	john@example.com	John Doe	$2a$10$k.3kysPaQvBkcU7CxI/SOO13IDOdZAW4gU5P4lyTgZYuIxLgJnaKO	USER
2af65f53-a898-48a9-8a53-71cc9dabc5dc	2025-03-26 11:15:46.849	anonymousUser	2025-03-26 11:15:46.849	anonymousUser	john@example.id	John Doe 2	$2a$10$5Ecgk/KCm.v06rG3LbWfKerl5fduBQmOTShT958NSOee42w0Lw8XG	USER
0b4fb228-0e8c-4f0a-a271-71a9d529c7a6	2025-03-26 08:21:02.288846	System	2025-03-26 08:21:02.288846	System	admin@library-muhriz.com	Admin	$2a$10$k.3kysPaQvBkcU7CxI/SOO13IDOdZAW4gU5P4lyTgZYuIxLgJnaKO	ADMIN
\.


--
-- TOC entry 4699 (class 2606 OID 16819)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- TOC entry 4705 (class 2606 OID 16857)
-- Name: loans loans_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loans
    ADD CONSTRAINT loans_pkey PRIMARY KEY (id);


--
-- TOC entry 4701 (class 2606 OID 16839)
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 4703 (class 2606 OID 16835)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4706 (class 2606 OID 16863)
-- Name: loans fk6xxlcjc0rqtn5nq28vjnx5t9d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loans
    ADD CONSTRAINT fk6xxlcjc0rqtn5nq28vjnx5t9d FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4707 (class 2606 OID 16858)
-- Name: loans fkokwvlrv6o4i4h3le3bwhe6kie; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loans
    ADD CONSTRAINT fkokwvlrv6o4i4h3le3bwhe6kie FOREIGN KEY (book_id) REFERENCES public.books(id);


-- Completed on 2025-03-27 08:06:23

--
-- PostgreSQL database dump complete
--

