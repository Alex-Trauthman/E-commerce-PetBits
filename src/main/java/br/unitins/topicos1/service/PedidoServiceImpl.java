package br.unitins.topicos1.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.dto.BrinquedoPedidoDTO;
import br.unitins.topicos1.dto.CartaoCreditoDTO;
import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.dto.PedidoResponseDTO;
import br.unitins.topicos1.dto.PetiscoPedidoDTO;
import br.unitins.topicos1.dto.PixDTO;
import br.unitins.topicos1.dto.RacaoPedidoDTO;
import br.unitins.topicos1.dto.RemedioPedidoDTO;
import br.unitins.topicos1.model.Brinquedo;
import br.unitins.topicos1.model.BrinquedoPedido;
import br.unitins.topicos1.model.Pedido;
import br.unitins.topicos1.model.Petisco;
import br.unitins.topicos1.model.PetiscoPedido;
import br.unitins.topicos1.model.Racao;
import br.unitins.topicos1.model.RacaoPedido;
import br.unitins.topicos1.model.Remedio;
import br.unitins.topicos1.model.RemedioPedido;
import br.unitins.topicos1.repository.BrinquedoPedidoRepository;
import br.unitins.topicos1.repository.BrinquedoRepository;
import br.unitins.topicos1.repository.ClienteRepository;
import br.unitins.topicos1.repository.PedidoRepository;
import br.unitins.topicos1.repository.PetiscoPedidoRepository;
import br.unitins.topicos1.repository.PetiscoRepository;
import br.unitins.topicos1.repository.RacaoPedidoRepository;
import br.unitins.topicos1.repository.RacaoRepository;
import br.unitins.topicos1.repository.RemedioPedidoRepository;
import br.unitins.topicos1.repository.RemedioRepository;
import br.unitins.topicos1.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    public PedidoRepository pedidoRepository;

    @Inject
    public ClienteRepository clienteRepository;

    @Inject
    public BrinquedoRepository brinquedoRepository;
    @Inject
    public RacaoRepository racaoRepository;
    @Inject
    public PetiscoRepository petiscoRepository;
    @Inject
    public RemedioRepository remedioRepository;
    @Inject
    public BrinquedoPedidoRepository brinquedoPedidoRepository;
    @Inject
    public PetiscoPedidoRepository petiscoPedidoRepository;
    @Inject
    public RemedioPedidoRepository remedioPedidoRepository;
    @Inject
    public RacaoPedidoRepository racaoPedidoRepository;
    @Inject
    public RacaoServiceImpl racaoService;
    @Inject
    public PetiscoServiceImpl petiscoService;
    @Inject
    public BrinquedoServiceImpl brinquedoService;
    @Inject
    public RemedioServiceImpl remedioService;

    @Override
    @Transactional
    public PedidoResponseDTO create(@Valid PedidoDTO dto) {

        Pedido pedido = new Pedido();
        double total =0;
        pedido.setDataExpiracao(LocalDateTime.now().plus(Duration.ofMinutes(20)));
        
        pedido.setCliente(clienteRepository.findByUsername(jsonWebToken.getName()));
        List<RacaoPedido> racao = new ArrayList<RacaoPedido>();

        for (RacaoPedidoDTO racaoDTO : dto.racao()) {
            Racao racaoBanco = racaoRepository.findById(racaoDTO.idRacao());
            validarQuantidade(racaoBanco.getEstoque(), racaoDTO.quantidade());
            racaoService.validarId(racaoDTO.idRacao());
            RacaoPedido racaoPedido = new RacaoPedido();
            racaoPedido.setPreco(racaoBanco.getPreco());
            racaoPedido.setDesconto(racaoDTO.desconto());
            racaoPedido.setRacao(racaoBanco);
            racaoPedido.setQuantidade(racaoDTO.quantidade());
            total += racaoPedido.getPreco()/(racaoDTO.desconto()/100+1)*racaoDTO.quantidade();
            racaoBanco.setEstoque(racaoBanco.getEstoque()-racaoDTO.quantidade());
            racaoPedidoRepository.persist(racaoPedido);
            racao.add(racaoPedido);
            
        }
        

        List<BrinquedoPedido> brinquedo = new ArrayList<BrinquedoPedido>();

        for (BrinquedoPedidoDTO brinquedoDTO : dto.brinquedo()) {
            Brinquedo brinquedoBanco = brinquedoRepository.findById(brinquedoDTO.idBrinquedo());
            brinquedoService.validarId(brinquedoDTO.idBrinquedo());
            validarQuantidade(brinquedoBanco.getEstoque(), brinquedoDTO.quantidade());
            BrinquedoPedido brinquedoPedido = new BrinquedoPedido();
            brinquedoPedido.setPreco(brinquedoBanco.getPreco());
            brinquedoPedido.setDesconto(brinquedoDTO.desconto());
            brinquedoPedido.setQuantidade(brinquedoDTO.quantidade());
            total += brinquedoPedido.getPreco()/(brinquedoDTO.desconto()/100+1)*brinquedoDTO.quantidade();
            brinquedoBanco.setEstoque(brinquedoBanco.getEstoque()-brinquedoDTO.quantidade());
            brinquedoPedido.setBrinquedo(brinquedoBanco);
            brinquedoPedidoRepository.persist(brinquedoPedido);
            brinquedo.add(brinquedoPedido);
            
        }
        List<PetiscoPedido> petisco = new ArrayList<PetiscoPedido>();

        for (PetiscoPedidoDTO petiscoDTO : dto.petisco()) {
            Petisco petiscoBanco = petiscoRepository.findById(petiscoDTO.idPetisco());
            petiscoService.validarId(petiscoDTO.idPetisco());
            validarQuantidade(petiscoBanco.getEstoque(),petiscoDTO.quantidade());
            PetiscoPedido petiscoPedido = new PetiscoPedido();
            petiscoPedido.setPreco(petiscoBanco.getPreco());
            petiscoPedido.setDesconto(petiscoDTO.desconto());
            petiscoPedido.setPetisco(petiscoBanco);
            petiscoPedido.setQuantidade(petiscoDTO.quantidade());
            total += petiscoPedido.getPreco()/(petiscoDTO.desconto()/100+1)*petiscoDTO.quantidade();
            petiscoBanco.setEstoque(petiscoBanco.getEstoque()-petiscoDTO.quantidade());
            petiscoPedidoRepository.persist(petiscoPedido);
            petisco.add(petiscoPedido);
        }
        List<RemedioPedido> remedio = new ArrayList<RemedioPedido>();

        for (RemedioPedidoDTO remedioDTO : dto.remedio()) {
            Remedio remedioBanco = remedioRepository.findById(remedioDTO.idRemedio());
            remedioService.validarId(remedioDTO.idRemedio());
            validarQuantidade(remedioBanco.getEstoque(), remedioDTO.quantidade());
            RemedioPedido remedioPedido = new RemedioPedido();
            remedioPedido.setPreco(remedioBanco.getPreco());
            remedioPedido.setDesconto(remedioDTO.desconto());
            remedioPedido.setRemedio(remedioBanco);
            remedioPedido.setQuantidade(remedioDTO.quantidade());
            total += remedioPedido.getPreco()/(remedioDTO.desconto()/100+1)*remedioDTO.quantidade();
            remedioBanco.setEstoque(remedioBanco.getEstoque()-remedioDTO.quantidade());
            remedioPedidoRepository.persist(remedioPedido);
            remedio.add(remedioPedido);
        }
        total = Math.round(total*100.0)/100.0;
        pedido.setTotal(total);
        pedido.setRemedio(remedio);
        pedido.setPetisco(petisco);
        pedido.setRacao(racao);
        pedido.setBrinquedo(brinquedo);
        pedido.setStatus("Não pago");
        pedidoRepository.persist(pedido);
        return PedidoResponseDTO.valueOf(pedido);
    }
    

    public void validarQuantidade(Integer quantidadeReal, Integer quantidadePedido) {
        if (quantidadeReal < quantidadePedido||quantidadePedido<0){
            throw new ValidationException("Quantidade", "Quantidade maior que o estoque");
        }
    }

    @Override
    public List<PedidoResponseDTO> findMyPedidos(){
        return findByCliente(clienteRepository.findByUsername(jsonWebToken.getName()).getId());
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null){
            return null;
        }
        return PedidoResponseDTO.valueOf(pedidoRepository.findById(id));
    }

    public void processarPedido(Long pedidoId, double valor) {
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new ValidationException("Pedido", "Pedido não encontrado.");
        }
    
        // Verifica se o pedido expirou
        if (pedido.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Pedido", "Pedido expirado.");
        }
        if(valor<pedido.getTotal()){
            throw new ValidationException("Valor", "Valor insuficiente.");
        }

    }

    @Override
    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository
        .listAll()
        .stream()
        .map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PedidoResponseDTO> findByCliente(Long idCliente) {
        return pedidoRepository.findByCliente(idCliente).stream()
        .map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }
    @Override
    @Transactional
    public void PagarPedidoCredito(Long id, @Valid CartaoCreditoDTO cartao) {
        Pedido pedidoPagar = pedidoRepository.findById(id);
        
        if (!isValidCreditCard(cartao.numero())) {
            throw new ValidationException("Numero", "Numero do Cartão inválido.");
        }

        processarPedido(id, cartao.limite());
        
        pedidoPagar.setStatus("Pago, Crédito");
    }
    @Override
    @Transactional
    public void PagarPedidoPix(@Valid PixDTO pix) {
        Pedido pedidoPagar = pedidoRepository.findById(pix.idPedido());
        processarPedido(pix.idPedido(), pix.valor());
        pedidoPagar.setStatus("Pago, Pix ");
    }
    public boolean isValidCreditCard(String cardNumber) {
        String cleanedCardNumber = cardNumber.replaceAll("\\D", "");

        String reversedCardNumber = new StringBuilder(cleanedCardNumber).reverse().toString();

        int sum = 0;
        boolean alternate = false;

        for (int i = 0; i < reversedCardNumber.length(); i++) {
            int digit = Character.getNumericValue(reversedCardNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }
    
}