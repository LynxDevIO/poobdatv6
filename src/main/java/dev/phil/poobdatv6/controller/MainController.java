package dev.phil.poobdatv6.controller;

import dev.phil.poobdatv6.database.DBConnection;
import dev.phil.poobdatv6.database.dao.*;
import dev.phil.poobdatv6.model.Cliente;
import dev.phil.poobdatv6.model.ItemVenda;
import dev.phil.poobdatv6.model.Municipio;
import dev.phil.poobdatv6.model.Produto;
import dev.phil.poobdatv6.model.UF;
import dev.phil.poobdatv6.model.Venda;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.SpinnerValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private IClienteDao clienteDao;
    private IProdutoDao produtoDao;
    private IVendaDao vendaDao;
    private IItemVendaDao itemVendaDao;
    private IUfDao ufDao;
    private IMunicipioDao municipioDao;

    // Aba de Cliente
    @FXML
    public TextField cadastroNomeCliente;
    public TextField cadastroCPFCliente;
    public TextField cadastroLogradouroCliente;
    public TextField cadastroEmailCliente;
    public DatePicker cadastroDataDeNascimentoCliente;
    public TextField cadastroBairroCliente;
    public ComboBox<Municipio> cadastroCidadeCliente;
    public ComboBox<UF> cadastroEstadoCliente;
    public Button cadastroCadastrarCliente;
    public Label statusConexaoBD;

    // Aba de Venda
    @FXML
    public TextField venda_id_cliente;
    public Label venda_nome_do_cliente;
    public ComboBox<Produto> venda_produtos;
    public Spinner<Integer> venda_produto_quantidade;
    public Button venda_produto_inserir;
    public Button venda_registrar_venda;
    public TableView<ItemVenda> venda_tabela_produtos;
    public TableColumn<ItemVenda, String> venda_tabela_produto;
    public TableColumn<ItemVenda, Integer> venda_tabela_quantidade;
    private List<ItemVenda> itensVendaAtual = new ArrayList<>();
    private Cliente clienteVendaAtual;

    public MainController() throws SQLException {
        clienteDao = new ClienteDao();
        produtoDao = new ProdutoDao();
        vendaDao = new VendaDao();
        itemVendaDao = new ItemVendaDao();
        ufDao = new UfDao();
        municipioDao = new MunicipioDao();

        clienteDao.criarTabela();
        produtoDao.criarTabela();
        vendaDao.criarTabela();
        itemVendaDao.criarTabela();

        ufDao.criarTabela();
        municipioDao.criarTabela();
    }

    @FXML
    public void initialize() throws SQLException {
        definirStatusDeConexao();
        try {
            // carregar UFs e municípios
            List<UF> ufs = ufDao.buscarTodas();
            cadastroEstadoCliente.setItems(FXCollections.observableArrayList(ufs));
            
            // listener para atualizar municípios quando o estado for selecionado
            cadastroEstadoCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    try {
                        List<Municipio> municipios = municipioDao.buscarPorUf(newVal.getId());
                        cadastroCidadeCliente.setItems(FXCollections.observableArrayList(municipios));
                        if (!municipios.isEmpty()) {
                            cadastroCidadeCliente.getSelectionModel().selectFirst();
                        }
                    } catch (SQLException e) {
                        mostrarErro("Erro ao carregar municípios", e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

            if (!ufs.isEmpty()) {
                cadastroEstadoCliente.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar estados", e.getMessage());
            e.printStackTrace();
        }
        
        // Configurar aba de vendas
        try {
            // Configurar o Spinner de quantidade
            SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
            venda_produto_quantidade.setValueFactory(valueFactory);
            
            // Configurar as colunas da tabela usando lambdas em vez de PropertyValueFactory
            venda_tabela_produto.setCellValueFactory(cellData -> {
                ItemVenda item = cellData.getValue();
                return new javafx.beans.property.SimpleStringProperty(
                    item.getProduto() != null ? item.getProduto().getNomeProduto() : ""
                );
            });
            
            venda_tabela_quantidade.setCellValueFactory(cellData -> {
                ItemVenda item = cellData.getValue();
                return new javafx.beans.property.SimpleIntegerProperty(
                    item.getQuantidade()
                ).asObject();
            });
            
            // Carregar produtos no ComboBox
            List<Produto> produtos = produtoDao.buscarProdutos();
            venda_produtos.setItems(FXCollections.observableArrayList(produtos));
            if (!produtos.isEmpty()) {
                venda_produtos.getSelectionModel().selectFirst();
            }
            
            // Adicionar listener para o botão de adicionar produto
            venda_produto_inserir.setOnAction(event -> adicionarProdutoAVenda());
            
            // Adicionar listener para o campo de ID do cliente
            venda_id_cliente.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    try {
                        int idCliente = Integer.parseInt(newValue);
                        buscarClientePorId(idCliente);
                    } catch (NumberFormatException e) {
                        venda_nome_do_cliente.setText("ID inválido");
                        clienteVendaAtual = null;
                    } catch (SQLException e) {
                        venda_nome_do_cliente.setText("Erro ao buscar cliente");
                        clienteVendaAtual = null;
                        e.printStackTrace();
                    }
                } else {
                    venda_nome_do_cliente.setText("");
                    clienteVendaAtual = null;
                }
            });
            
            // Limpar a tabela de produtos
            venda_tabela_produtos.getItems().clear();
            itensVendaAtual.clear();
            
        } catch (SQLException e) {
            mostrarErro("Erro ao configurar aba de vendas", e.getMessage());
            e.printStackTrace();
        }
    }

    private void definirStatusDeConexao() throws SQLException {
        if (DBConnection.getInstance().getConnection() != null && !DBConnection.getInstance().getConnection().isClosed()) {
            statusConexaoBD.setText("Conectado ao banco de dados");
            statusConexaoBD.setStyle("-fx-text-fill: green;");
        } else {
            statusConexaoBD.setText("Não conectado ao banco de dados");
            statusConexaoBD.setStyle("-fx-text-fill: red;");
        }
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarInformacao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    protected void registerSale() {
        if (clienteVendaAtual == null) {
            mostrarErro("Erro", "Selecione um cliente para a venda.");
            return;
        }
        
        if (itensVendaAtual.isEmpty()) {
            mostrarErro("Erro", "Adicione pelo menos um produto à venda.");
            return;
        }
        
        try {
            // Criar a venda
            Venda venda = new Venda();
            venda.setClienteVenda(clienteVendaAtual);
            venda.setDataVenda(new java.sql.Date(System.currentTimeMillis()));
            
            // Calcular o total da venda
            float totalVenda = 0;
            for (ItemVenda item : itensVendaAtual) {
                totalVenda += item.getValorTotal();
            }
            venda.setTotalVenda(totalVenda);
            
            // Inserir a venda no banco de dados
            vendaDao.inserirVenda(venda);
            
            // Obter o ID da venda inserida
            List<Venda> vendas = vendaDao.buscarVendas();
            Venda vendaInserida = vendas.get(vendas.size() - 1);
            
            // Inserir os itens da venda
            for (ItemVenda item : itensVendaAtual) {
                item.setVenda(vendaInserida);
                itemVendaDao.inserirItem(item);
                
                // Atualizar o estoque do produto
                Produto produto = item.getProduto();
                produto.setQuantidadeEstocada(produto.getQuantidadeEstocada() - item.getQuantidade());
                produtoDao.alterarProdutoPorId(produto.getIdProduto(), produto);
            }
            
            // Limpar a tabela e a lista de itens
            itensVendaAtual.clear();
            venda_tabela_produtos.getItems().clear();
            
            // Limpar o campo de cliente
            venda_id_cliente.clear();
            venda_nome_do_cliente.setText("");
            clienteVendaAtual = null;
            
            mostrarInformacao("Sucesso", "Venda registrada com sucesso!");
            
        } catch (SQLException e) {
            mostrarErro("Erro ao registrar venda", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void onActionCadastrarCliente() throws SQLException {
        Cliente cliente;
        if (DBConnection.getInstance().getConnection().isClosed()
                || DBConnection.getInstance().getConnection() == null) {
            mostrarErro("Erro", "Falha na conexão com o banco de dados.");
            return;
        }
        if (cadastroNomeCliente.getText().isEmpty()
                || cadastroCPFCliente.getText().isEmpty()
                || cadastroLogradouroCliente.getText().isEmpty()
                || cadastroDataDeNascimentoCliente.getValue() == null
                || cadastroBairroCliente.getText().isEmpty()
                || cadastroCidadeCliente.getValue() == null
                || cadastroEstadoCliente.getValue() == null) {
            mostrarErro("Erro", "Algum campo está vazio.");
            return;
        }
        cliente = getCliente();
        if (cliente == null) {
            return;
        }
        clienteDao.inserirCliente(cliente);
        mostrarInformacao("Sucesso!", "Cliente cadastrado com sucesso!");
    }

    private Cliente getCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpfCliente(cadastroCPFCliente.getText());
        cliente.setNomeCliente(cadastroNomeCliente.getText());
        cliente.setEmailCliente(cadastroEmailCliente.getText());

        if (cadastroDataDeNascimentoCliente.getValue() != null) {
            java.sql.Date dataNascimento = java.sql.Date.valueOf(cadastroDataDeNascimentoCliente.getValue());
            cliente.setDataNascimento(dataNascimento);
        } else {
            mostrarErro("Erro", "Data de nascimento não pode ser vazia.");
            return null;
        }

        if (!validarCPF()) {
            mostrarErro("Erro", "CPF invalido. Apenas números e sem símbolos.");
            return null;
        }

        if (!checarEmail()) {
            mostrarErro("Erro", "Email em formato incorreto.");
            return null;
        }
        
        Municipio municipio = cadastroCidadeCliente.getValue();
        UF uf = cadastroEstadoCliente.getValue();
        
        cliente.setEnderecoCliente(new String[]{
                cadastroLogradouroCliente.getText(),
                cadastroBairroCliente.getText(),
                municipio != null ? municipio.getNome() : "",
                uf != null ? uf.getSigla() : ""
        });
        return cliente;
    }

    private boolean validarCPF() {
        String cpf = cadastroCPFCliente.getText();
        return cpf.length() == 11 && cpf.matches("[0-9]{11}");
    }

    private boolean checarEmail() {
        String email = cadastroEmailCliente.getText().trim();
        int arrobas = 0;
        int pontosDepoisDoArroba = 0;
        boolean encontrado = false;
        for (int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);

            if (c == '@') {
                arrobas++;
                encontrado = true;
            } else if (c == '.' && encontrado) {
                pontosDepoisDoArroba++;
            }
        }

        return arrobas == 1 && pontosDepoisDoArroba > 0;
    }

    private void adicionarProdutoAVenda() {
        Produto produtoSelecionado = venda_produtos.getValue();
        if (produtoSelecionado == null) {
            mostrarErro("Erro", "Selecione um produto.");
            return;
        }
        
        int quantidade = venda_produto_quantidade.getValue();
        if (quantidade <= 0) {
            mostrarErro("Erro", "A quantidade deve ser maior que zero.");
            return;
        }
        
        // Verificar se o produto já está na lista
        for (ItemVenda item : itensVendaAtual) {
            if (item.getProduto().getIdProduto() == produtoSelecionado.getIdProduto()) {
                // Atualizar a quantidade
                item.setQuantidade(item.getQuantidade() + quantidade);
                item.setValorTotal(item.getQuantidade() * produtoSelecionado.getPrecoVendaProduto());
                venda_tabela_produtos.refresh();
                return;
            }
        }
        
        // Criar um novo item de venda
        ItemVenda novoItem = new ItemVenda();
        novoItem.setProduto(produtoSelecionado);
        novoItem.setQuantidade(quantidade);
        novoItem.setValorTotal(quantidade * produtoSelecionado.getPrecoVendaProduto());
        
        // Adicionar à lista e à tabela
        itensVendaAtual.add(novoItem);
        venda_tabela_produtos.setItems(FXCollections.observableArrayList(itensVendaAtual));
    }

    private void buscarClientePorId(int idCliente) throws SQLException {
        Cliente cliente = clienteDao.buscarClientePorId(idCliente);
        if (cliente != null) {
            venda_nome_do_cliente.setText(cliente.getNomeCliente());
            clienteVendaAtual = cliente;
        } else {
            venda_nome_do_cliente.setText("Cliente não encontrado");
            clienteVendaAtual = null;
        }
    }
}