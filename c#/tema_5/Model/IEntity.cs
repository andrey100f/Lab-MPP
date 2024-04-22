namespace Model
{
    public interface IEntity<TId>
    {
        TId Id { get; set; }
    }
}
