-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2021 at 12:48 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `banksystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `agencias`
--

CREATE TABLE `agencias` (
  `id` int(11) NOT NULL,
  `idDadosPessoais` int(20) NOT NULL,
  `nomeAgencia` varchar(50) NOT NULL,
  `estadoAgencia` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `agencias`
--

INSERT INTO `agencias` (`id`, `idDadosPessoais`, `nomeAgencia`, `estadoAgencia`) VALUES
(19, 111111111, 'BPI NET', 0),
(20, 333333333, 'Caixa Geral', 0);

-- --------------------------------------------------------

--
-- Table structure for table `agenciasestado`
--

CREATE TABLE `agenciasestado` (
  `id` int(11) NOT NULL,
  `estadoLabel` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `agenciasestado`
--

INSERT INTO `agenciasestado` (`id`, `estadoLabel`) VALUES
(0, 'Ativa'),
(1, 'Desativa');

-- --------------------------------------------------------

--
-- Table structure for table `contabancaria`
--

CREATE TABLE `contabancaria` (
  `id` int(11) NOT NULL,
  `idConta` int(11) NOT NULL,
  `saldo` float NOT NULL,
  `dataAbertura` datetime DEFAULT current_timestamp(),
  `iban` varchar(50) NOT NULL,
  `idAgencia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `contabancaria`
--

INSERT INTO `contabancaria` (`id`, `idConta`, `saldo`, `dataAbertura`, `iban`, `idAgencia`) VALUES
(18, 19, 2000, '2021-06-17 22:13:01', 'PT50133716120310215849026', 19),
(19, 20, 147.2, '2021-06-17 22:13:28', 'PT50123965774111071598974', 20);

-- --------------------------------------------------------

--
-- Table structure for table `contaid`
--

CREATE TABLE `contaid` (
  `idConta` int(11) NOT NULL,
  `idDadosPessoais` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `contaid`
--

INSERT INTO `contaid` (`idConta`, `idDadosPessoais`) VALUES
(20, 232323232),
(19, 777347777);

-- --------------------------------------------------------

--
-- Table structure for table `contasolicitada`
--

CREATE TABLE `contasolicitada` (
  `idAuto` int(11) NOT NULL,
  `idDadosPessoais` int(20) NOT NULL,
  `idAgencia` int(11) NOT NULL,
  `estado` int(11) NOT NULL DEFAULT 1,
  `nota` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `contasolicitada`
--

INSERT INTO `contasolicitada` (`idAuto`, `idDadosPessoais`, `idAgencia`, `estado`, `nota`) VALUES
(36, 777347777, 19, 0, 'Conta Aceite'),
(37, 232323232, 20, 0, 'Sem Nota');

-- --------------------------------------------------------

--
-- Table structure for table `dadoslogin`
--

CREATE TABLE `dadoslogin` (
  `id` int(10) NOT NULL,
  `idDadosPessoais` int(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `tipo` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dadoslogin`
--

INSERT INTO `dadoslogin` (`id`, `idDadosPessoais`, `username`, `password`, `tipo`) VALUES
(53, 111111111, 'gestor1', 'd033e22ae348aeb5660fc2140aec35850c4da997', 2),
(54, 222222222, 'gestor2', 'd033e22ae348aeb5660fc2140aec35850c4da997', 2),
(55, 333333333, 'gestor3', 'd033e22ae348aeb5660fc2140aec35850c4da997', 2),
(56, 444444444, 'admin1', 'd033e22ae348aeb5660fc2140aec35850c4da997', 3),
(57, 555555555, 'admin2', 'd033e22ae348aeb5660fc2140aec35850c4da997', 3),
(58, 666666666, 'admin3', 'd033e22ae348aeb5660fc2140aec35850c4da997', 3),
(61, 777347777, 'ricardo123', 'd033e22ae348aeb5660fc2140aec35850c4da997', 0),
(62, 232323232, 'andre123', 'd033e22ae348aeb5660fc2140aec35850c4da997', 0),
(63, 456654654, 'funcionario', 'd033e22ae348aeb5660fc2140aec35850c4da997', 1);

-- --------------------------------------------------------

--
-- Table structure for table `dadospessoais`
--

CREATE TABLE `dadospessoais` (
  `nif` int(20) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `dtNascimento` varchar(50) NOT NULL,
  `idade` int(3) NOT NULL,
  `CodCC` varchar(50) NOT NULL,
  `situacaoLaboral` varchar(50) NOT NULL,
  `numTelemovel` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dadospessoais`
--

INSERT INTO `dadospessoais` (`nif`, `nome`, `dtNascimento`, `idade`, `CodCC`, `situacaoLaboral`, `numTelemovel`) VALUES
(111111111, 'Ricardo Xavier XPTO1', '18/06/1997', 23, '14253648X01ZY400', 'Gestor', '+251258234123'),
(222222222, 'Andre Torres XPTO2', '06/06/1997', 24, '14253648X01ZY500', 'Gestor', '123523212'),
(232323232, 'Andre Torre Gonçalves', '18/06/1998', 22, '285244514ZF231', 'Programador', '923421424'),
(333333333, 'Joao Antonio XPTO', '17/06/1987', 34, '14253648X01ZY600', 'Gestor', '8723124524'),
(444444444, 'Joca Tpaine', '18/06/1992', 28, '14253648X01ZY700', 'Administrador', '823425242'),
(456654654, 'Funcionario XPTO123', '19/06/1997', 23, '1234241APT23', 'Funcionario do Banco', '1111333444'),
(555555555, 'Claudio Santos', '06/06/1996', 25, '14253648X01ZY900', 'Administrador Banco', '8882223331'),
(666666666, 'Joca Mike Mario', '23/06/1997', 23, '14253648X01ZY940', 'Admin', '222333444'),
(777347777, 'Ricardo X Martin Amaro 123', '18/06/1998', 22, '123452412AR23', 'Gestor de Empresas', '8734244124');

-- --------------------------------------------------------

--
-- Table structure for table `estadocsolicitadas`
--

CREATE TABLE `estadocsolicitadas` (
  `idEstado` int(11) NOT NULL,
  `label` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `estadocsolicitadas`
--

INSERT INTO `estadocsolicitadas` (`idEstado`, `label`) VALUES
(0, 'Conta Aceite'),
(1, 'Pendente'),
(2, 'Rejeitada');

-- --------------------------------------------------------

--
-- Table structure for table `estadomarcacoes`
--

CREATE TABLE `estadomarcacoes` (
  `id` int(11) NOT NULL,
  `label` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `estadomarcacoes`
--

INSERT INTO `estadomarcacoes` (`id`, `label`) VALUES
(1, 'Pendete'),
(2, 'Atribuido'),
(3, 'Finalizada'),
(4, 'Em progresso'),
(5, 'Eliminada');

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `id` int(11) NOT NULL,
  `idDadosPessoais` int(20) NOT NULL,
  `tipolog` varchar(200) NOT NULL,
  `data` datetime DEFAULT current_timestamp(),
  `descricao` varchar(50) NOT NULL,
  `idContaBancaria` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`id`, `idDadosPessoais`, `tipolog`, `data`, `descricao`, `idContaBancaria`) VALUES
(5, 777347777, 'DEPOSITO', '2021-06-17 22:53:40', 'DEPOSITOU 2342.2EUR', 19),
(6, 777347777, 'LEVANTAMENTO', '2021-06-17 22:53:50', 'LEVANTOU 234.0EUR', 19),
(7, 777347777, 'PAGAMENTO', '2021-06-17 22:53:59', 'PAGAMENTO 223.0EUR', 19),
(8, 777347777, 'TRANSFERENCIA', '2021-06-17 22:54:24', 'TRANSFERENCIA 67.2EUR', 19);

-- --------------------------------------------------------

--
-- Table structure for table `marcacoesatribuidas`
--

CREATE TABLE `marcacoesatribuidas` (
  `id` int(11) NOT NULL,
  `idMarcacao` int(11) NOT NULL,
  `nifFuncionario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `marcacoesatribuidas`
--

INSERT INTO `marcacoesatribuidas` (`id`, `idMarcacao`, `nifFuncionario`) VALUES
(9, 9, 456654654);

-- --------------------------------------------------------

--
-- Table structure for table `marcacoespendentes`
--

CREATE TABLE `marcacoespendentes` (
  `id` int(11) NOT NULL,
  `nifCliente` int(11) NOT NULL,
  `dataHora` varchar(200) NOT NULL,
  `descricao` varchar(200) DEFAULT NULL,
  `estado` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `marcacoespendentes`
--

INSERT INTO `marcacoespendentes` (`id`, `nifCliente`, `dataHora`, `descricao`, `estado`) VALUES
(9, 777347777, '2021-06-18 | 11 horas', 'Mudar o nome da pessoa', 2);

-- --------------------------------------------------------

--
-- Table structure for table `morada`
--

CREATE TABLE `morada` (
  `id` int(11) NOT NULL,
  `morada` varchar(30) NOT NULL,
  `codigoP` varchar(20) NOT NULL,
  `cidade` varchar(20) NOT NULL,
  `idDadosPessoais` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `morada`
--

INSERT INTO `morada` (`id`, `morada`, `codigoP`, `cidade`, `idDadosPessoais`) VALUES
(31, 'Rua do Amaro XPTO', '4900-234', 'Viana do Castelo', 111111111),
(32, 'Rua do Amaro XPTOV2', '49450-234', 'Viana do Castelo', 222222222),
(33, 'Rua do Andre XPTO', '2342-232', 'Porto', 333333333),
(34, 'Rua das Flores', '5233-232', 'Viana do Castelo', 444444444),
(35, 'Rua das Pevides', '2342-234', 'Lisboa', 555555555),
(36, 'Rua do Benjamim', '1234-232', 'Vila Praia de Ancora', 666666666),
(39, 'Freixieiro de Soutelo', '1234-234', 'Viana do Castelo', 777347777),
(40, 'Monserrate', '7234-234', 'Viana do Castelo', 232323232),
(41, 'Viana do Castelo', '2344-234', 'Viana do Castelo', 456654654);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agencias`
--
ALTER TABLE `agencias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idDadosPessoais` (`idDadosPessoais`),
  ADD KEY `estadoAgencia` (`estadoAgencia`);

--
-- Indexes for table `agenciasestado`
--
ALTER TABLE `agenciasestado`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `contabancaria`
--
ALTER TABLE `contabancaria`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idConta` (`idConta`),
  ADD KEY `idAgencia` (`idAgencia`);

--
-- Indexes for table `contaid`
--
ALTER TABLE `contaid`
  ADD PRIMARY KEY (`idConta`),
  ADD KEY `idDadosPessoais` (`idDadosPessoais`);

--
-- Indexes for table `contasolicitada`
--
ALTER TABLE `contasolicitada`
  ADD PRIMARY KEY (`idAuto`),
  ADD KEY `idDadosPessoais` (`idDadosPessoais`),
  ADD KEY `idAgencia` (`idAgencia`),
  ADD KEY `estado` (`estado`);

--
-- Indexes for table `dadoslogin`
--
ALTER TABLE `dadoslogin`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idDadosPessoais` (`idDadosPessoais`);

--
-- Indexes for table `dadospessoais`
--
ALTER TABLE `dadospessoais`
  ADD PRIMARY KEY (`nif`);

--
-- Indexes for table `estadocsolicitadas`
--
ALTER TABLE `estadocsolicitadas`
  ADD PRIMARY KEY (`idEstado`);

--
-- Indexes for table `estadomarcacoes`
--
ALTER TABLE `estadomarcacoes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `marcacoesatribuidas`
--
ALTER TABLE `marcacoesatribuidas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nifFuncionario` (`nifFuncionario`),
  ADD KEY `idMarcacao` (`idMarcacao`);

--
-- Indexes for table `marcacoespendentes`
--
ALTER TABLE `marcacoespendentes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nifCliente` (`nifCliente`),
  ADD KEY `estado` (`estado`);

--
-- Indexes for table `morada`
--
ALTER TABLE `morada`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idDadosPessoais` (`idDadosPessoais`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `agencias`
--
ALTER TABLE `agencias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `agenciasestado`
--
ALTER TABLE `agenciasestado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `contabancaria`
--
ALTER TABLE `contabancaria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `contaid`
--
ALTER TABLE `contaid`
  MODIFY `idConta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `contasolicitada`
--
ALTER TABLE `contasolicitada`
  MODIFY `idAuto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `dadoslogin`
--
ALTER TABLE `dadoslogin`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `estadocsolicitadas`
--
ALTER TABLE `estadocsolicitadas`
  MODIFY `idEstado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `estadomarcacoes`
--
ALTER TABLE `estadomarcacoes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `marcacoesatribuidas`
--
ALTER TABLE `marcacoesatribuidas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `marcacoespendentes`
--
ALTER TABLE `marcacoespendentes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `morada`
--
ALTER TABLE `morada`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `agencias`
--
ALTER TABLE `agencias`
  ADD CONSTRAINT `agencias_ibfk_1` FOREIGN KEY (`idDadosPessoais`) REFERENCES `dadospessoais` (`nif`),
  ADD CONSTRAINT `agencias_ibfk_2` FOREIGN KEY (`estadoAgencia`) REFERENCES `agenciasestado` (`id`);

--
-- Constraints for table `contabancaria`
--
ALTER TABLE `contabancaria`
  ADD CONSTRAINT `contabancaria_ibfk_1` FOREIGN KEY (`idConta`) REFERENCES `contaid` (`idConta`),
  ADD CONSTRAINT `contabancaria_ibfk_2` FOREIGN KEY (`idAgencia`) REFERENCES `agencias` (`id`);

--
-- Constraints for table `contaid`
--
ALTER TABLE `contaid`
  ADD CONSTRAINT `contaid_ibfk_1` FOREIGN KEY (`idDadosPessoais`) REFERENCES `dadospessoais` (`nif`);

--
-- Constraints for table `contasolicitada`
--
ALTER TABLE `contasolicitada`
  ADD CONSTRAINT `contasolicitada_ibfk_1` FOREIGN KEY (`idDadosPessoais`) REFERENCES `dadospessoais` (`nif`),
  ADD CONSTRAINT `contasolicitada_ibfk_2` FOREIGN KEY (`idAgencia`) REFERENCES `agencias` (`id`),
  ADD CONSTRAINT `contasolicitada_ibfk_3` FOREIGN KEY (`estado`) REFERENCES `estadocsolicitadas` (`idEstado`);

--
-- Constraints for table `dadoslogin`
--
ALTER TABLE `dadoslogin`
  ADD CONSTRAINT `dadoslogin_ibfk_1` FOREIGN KEY (`idDadosPessoais`) REFERENCES `dadospessoais` (`nif`);

--
-- Constraints for table `marcacoesatribuidas`
--
ALTER TABLE `marcacoesatribuidas`
  ADD CONSTRAINT `marcacoesatribuidas_ibfk_1` FOREIGN KEY (`nifFuncionario`) REFERENCES `dadospessoais` (`nif`),
  ADD CONSTRAINT `marcacoesatribuidas_ibfk_2` FOREIGN KEY (`idMarcacao`) REFERENCES `marcacoespendentes` (`id`);

--
-- Constraints for table `marcacoespendentes`
--
ALTER TABLE `marcacoespendentes`
  ADD CONSTRAINT `marcacoespendentes_ibfk_1` FOREIGN KEY (`nifCliente`) REFERENCES `dadospessoais` (`nif`),
  ADD CONSTRAINT `marcacoespendentes_ibfk_2` FOREIGN KEY (`estado`) REFERENCES `estadomarcacoes` (`id`);

--
-- Constraints for table `morada`
--
ALTER TABLE `morada`
  ADD CONSTRAINT `morada_ibfk_1` FOREIGN KEY (`idDadosPessoais`) REFERENCES `dadospessoais` (`nif`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
